package com.mylearning.constants

import com.mylearning.exception.FatalException
import spock.lang.Unroll

class LandSpec extends spock.lang.Specification {
    @Unroll
    def "LandSpec - check cost Parameter updates based on the type of land"() {
        given:
        CostItems.fuelUsageLabel.incrementQuantity(-CostItems.fuelUsageLabel.getQuantityForCostItem())
        CostItems.paintDamageLabel.incrementQuantity(-CostItems.paintDamageLabel.getQuantityForCostItem())
        CostItems.destructionProtectedTreeLabel.incrementQuantity(-CostItems.destructionProtectedTreeLabel.getQuantityForCostItem())

        when:
        land.updateCostParameters(isDestination)

        then:
        CostItems.fuelUsageLabel.getQuantityForCostItem() == fuelUsageCount
        CostItems.paintDamageLabel.getQuantityForCostItem() == paintDamageCount
        CostItems.destructionProtectedTreeLabel.getQuantityForCostItem() == protectTreeDestructionCount

        where:
        scenario | land   | isDestination | fuelUsageCount | paintDamageCount | protectTreeDestructionCount
        1        | Land.r | false         | 2              | 0                | 0
        2        | Land.t | true          | 2              | 0                | 0
        3        | Land.o | true          | 1              | 0                | 0
        4        | Land.o | false         | 1              | 0                | 0
        5        | Land.r | true          | 2              | 0                | 0
        6        | Land.t | false         | 2              | 1                | 0
    }

    @Unroll
    def "LandSpec - check cost Parameter updates based on the T land"() {
        given:
        CostItems.fuelUsageLabel.incrementQuantity(-CostItems.fuelUsageLabel.getQuantityForCostItem())
        CostItems.paintDamageLabel.incrementQuantity(-CostItems.paintDamageLabel.getQuantityForCostItem())
        CostItems.destructionProtectedTreeLabel.incrementQuantity(-CostItems.destructionProtectedTreeLabel.getQuantityForCostItem())

        when:
        land.updateCostParameters(isDestination)

        then:
        CostItems.fuelUsageLabel.getQuantityForCostItem() == fuelUsageCount
        CostItems.paintDamageLabel.getQuantityForCostItem() == paintDamageCount
        CostItems.destructionProtectedTreeLabel.getQuantityForCostItem() == protectTreeDestructionCount

        thrown(FatalException)

        where:
        scenario | land   | isDestination | fuelUsageCount | paintDamageCount | protectTreeDestructionCount
        1        | Land.T | false         | 2              | 0                | 1
        2        | Land.T | true          | 2              | 0                | 1

    }
}
