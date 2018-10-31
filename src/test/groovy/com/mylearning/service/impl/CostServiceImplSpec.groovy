package com.mylearning.service.impl

import com.mylearning.constants.CostItems
import com.mylearning.constants.Land
import com.mylearning.constants.SimulatorConstants
import com.mylearning.exception.FatalException
import com.mylearning.model.Simulator
import com.mylearning.model.Square
import com.mylearning.utils.FileLoaderUtil
import com.mylearning.utils.StaticDataLoaderUtil
import spock.lang.Specification
import spock.lang.Unroll

class CostServiceImplSpec extends Specification {
    def costService = new CostServiceImpl();

    @Unroll
    def "UpdateCost"() {
        given:
        Simulator.currentSquare = new Square();
        Simulator.currentSquare.setLand(land)
        Simulator.currentSquare.setIsCleared(isCleared)

        CostItems.fuelUsageLabel.incrementQuantity(-CostItems.fuelUsageLabel.getQuantityForCostItem())
        CostItems.paintDamageLabel.incrementQuantity(-CostItems.paintDamageLabel.getQuantityForCostItem())
        CostItems.destructionProtectedTreeLabel.incrementQuantity(-CostItems.destructionProtectedTreeLabel.getQuantityForCostItem())


        when:
        costService.updateCost(isDestination)

        then:
        CostItems.fuelUsageLabel.getQuantityForCostItem() == fuelCount
        CostItems.paintDamageLabel.getQuantityForCostItem() == paintDamageCount
        CostItems.destructionProtectedTreeLabel.getQuantityForCostItem() == destructionOfProtectedTrees

        Simulator.currentSquare.getIsCleared() == true

        where:
        scenario | land   | isCleared | isDestination | fuelCount | paintDamageCount | destructionOfProtectedTrees
        1        | Land.o | false     | false         | 1         | 0                | 0
        2        | Land.o | true      | false         | 1         | 0                | 0
        3        | Land.o | true      | true          | 1         | 0                | 0
        4        | Land.o | false     | true          | 1         | 0                | 0

        5        | Land.r | false     | false         | 2         | 0                | 0
        6        | Land.r | true      | false         | 1         | 0                | 0
        7        | Land.r | true      | true          | 1         | 0                | 0
        8        | Land.r | false     | true          | 2         | 0                | 0

        9        | Land.t | false     | false         | 2         | 1                | 0
        10       | Land.t | true      | false         | 1         | 0                | 0
        11       | Land.t | true      | true          | 1         | 0                | 0
        12       | Land.t | false     | true          | 2         | 0                | 0
    }

    @Unroll
    def "UpdateCost for Protected Tree"() {
        given:
        Simulator.currentSquare = new Square();
        Simulator.currentSquare.setLand(land)

        CostItems.fuelUsageLabel.incrementQuantity(-CostItems.fuelUsageLabel.getQuantityForCostItem())
        CostItems.paintDamageLabel.incrementQuantity(-CostItems.paintDamageLabel.getQuantityForCostItem())
        CostItems.destructionProtectedTreeLabel.incrementQuantity(-CostItems.destructionProtectedTreeLabel.getQuantityForCostItem())

        when:
        costService.updateCost(isDestination)

        then:
        thrown(FatalException)
        CostItems.fuelUsageLabel.getQuantityForCostItem() == fuelCount
        CostItems.paintDamageLabel.getQuantityForCostItem() == paintDamageCount
        CostItems.destructionProtectedTreeLabel.getQuantityForCostItem() == destructionOfProtectedTrees

        where:
        scenario | land   | isDestination | fuelCount | paintDamageCount | destructionOfProtectedTrees
        1        | Land.T | false         | 2         | 0                | 1
        2        | Land.T | true          | 2         | 0                | 1
    }

    def "IncrementCommunicationOverhead"() {
        given:
        CostItems.communicationOverheadLabel.incrementQuantity(-CostItems.communicationOverheadLabel.getQuantityForCostItem())
        when:
        costService.incrementCommunicationOverhead()
        then:
        CostItems.communicationOverheadLabel.getQuantityForCostItem() == 1
    }

    @Unroll
    def "UpdateTotal"() {
        given:
        FileLoaderUtil.populateProperties(SimulatorConstants.CONFIG_PATH, SimulatorConstants.PROPERTIES);
        FileLoaderUtil.populateProperties(SimulatorConstants.CONTENT_FILE_PATH, SimulatorConstants.TEXT);
        def intCredits = StaticDataLoaderUtil.getConfigValueAsInt(credits)

        when:
        def cost = costService.updateTotal(label, count, intCredits)

        then:
        cost == total

        where:
        scenario | label                                               | count | credits                                             | total
        1        | SimulatorConstants.COMMUNICATION_OVERHEAD_LABEL     | 7  | SimulatorConstants.COMMUNICATION_OVERHEAD_CREDIT    | 7
        2        | SimulatorConstants.FUEL_USAGE_LABEL                 | 19 | SimulatorConstants.FUEL_CREDIT                      | 19
        3        | SimulatorConstants.UN_CLEARED_SQUARES_LABEL         | 34 | SimulatorConstants.UNCLEARED_SQUARE_CREDIT          | 102
        4        | SimulatorConstants.DESTRUCTION_PROTECTED_TREE_LABEL | 0  | SimulatorConstants.PROTECTED_TREEDESTRUCTION_CREDIT | 0
        5        | SimulatorConstants.PAINT_DAMAGE_LABEL               | 1  | SimulatorConstants.PAINT_DAMAGE_CREDIT              | 2

    }


    def "DisplayCost"() {
        given:
        FileLoaderUtil.populateProperties(SimulatorConstants.CONFIG_PATH, SimulatorConstants.PROPERTIES);
        FileLoaderUtil.populateProperties(SimulatorConstants.CONTENT_FILE_PATH, SimulatorConstants.TEXT);

        CostItems.fuelUsageLabel.incrementQuantity(-CostItems.fuelUsageLabel.getQuantityForCostItem()+19)
        CostItems.paintDamageLabel.incrementQuantity(-CostItems.paintDamageLabel.getQuantityForCostItem()+1)
        CostItems.destructionProtectedTreeLabel.incrementQuantity(-CostItems.destructionProtectedTreeLabel.getQuantityForCostItem()+0)
        CostItems.unClearedSquaresLabel.incrementQuantity(-CostItems.unClearedSquaresLabel.getQuantityForCostItem()+34)
        CostItems.communicationOverheadLabel.incrementQuantity(-CostItems.communicationOverheadLabel.getQuantityForCostItem()+7)

        when:
        def total = costService.displayCost()

        then:
        total == 130
    }

}
