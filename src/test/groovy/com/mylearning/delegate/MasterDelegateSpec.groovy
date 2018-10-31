package com.mylearning.delegate

import com.mylearning.constants.CostItems
import com.mylearning.constants.SimulatorConstants
import com.mylearning.exception.InValidDataException

import com.mylearning.model.Simulator
import com.mylearning.model.Square
import com.mylearning.service.CostService
import spock.lang.Specification
import spock.lang.Unroll

class MasterDelegateSpec extends Specification {
    def masterDelegate = new MasterDelegate()
    def costService = Mock(CostService)

    @Unroll
    def "ShutDownSimulatorOnError"() {
        given:
        masterDelegate.setShutDownDelegate(new ShutDownDelegate(costService: costService))

        when:
        masterDelegate.shutDownSimulatorOnError(message)

        then:
        1 * costService.displayCost()

        where:
        scenario | message
        1        | "Invalid Path"
        2        | SimulatorConstants.NAVIGATION_BEYOND_SITE
        3        | SimulatorConstants.PROTECTED_TREE_DESTRUCTION_TEXT
    }

    @Unroll
    def "ShutDownSimulator"() {
        given:
        masterDelegate.setShutDownDelegate(new ShutDownDelegate(costService: costService))

        when:
        masterDelegate.shutDownSimulator(isDestination)

        then:
        count * costService.displayCost()

        where:
        scenario | isDestination | count
        1        | true          | 1
        2        | false         | 0
    }

    @Unroll
    def "ExecuteOperation"() {
        given:
        def input = new Scanner(operations)
        masterDelegate.setShutDownDelegate(new ShutDownDelegate(costService: costService))
        Simulator.squares = new Square[10][10]
        Simulator.currentSquare = new Square()
        Simulator.squares[0][1] = new Square()

        when:
        def result = masterDelegate.executeOperation(input)

        then:
        result == isTerminated
        count * costService.displayCost()

        where:
        scenario | operations  | isTerminated | count
        1        | "Advance 2" | false        | 0
        2        | "r"         | false        | 0
        3        | "Left"      | false        | 0
        4        | "q"         | true         | 1
    }

    @Unroll
    def "ExecuteOperation handle errors"() {
        given:
        def input = new Scanner(operations)
        masterDelegate.setShutDownDelegate(new ShutDownDelegate(costService: costService))
        Simulator.squares = new Square[10][10]
        Simulator.currentSquare = new Square()
        Simulator.squares[0][1] = new Square()

        when:
        masterDelegate.executeOperation(input)

        then:
        thrown(InValidDataException)


        where:
        scenario | operations
        1        | "hvhgcv"
        2        | "A"
        3        | "null"
        4        | "A3"
    }

    @Unroll
    def "SetupSimulator"() {
        given:
        def args = new String[2];
        args[0] = file
        when:
        masterDelegate.setupSimulator(args)
        then:
        thrown(InValidDataException)

        where:
        scenario | file
        1        | ""
        2        | null
        3        | "sitt"

    }

    @Unroll
    def "SetupSimulator - valid file"() {
        given:
        def args = new String[2];
        args[0] = file
        CostItems.unClearedSquaresLabel.incrementQuantity(-CostItems.unClearedSquaresLabel.getQuantityForCostItem())

        when:
        masterDelegate.setupSimulator(args)
        then:
        Simulator.squares != null
        Simulator.squares.length == 5
        Simulator.squares[0].length == 10

        CostItems.unClearedSquaresLabel.getQuantityForCostItem() == 48

        where:
        scenario | file
        1        | "siteMap"
        2        | "siteMap.txt"

    }

    @Unroll
    def "SetupDelegate ValidateFile"() {
        when:
        new SetupDelegate().ValidateFile(values)

        then:
        thrown(InValidDataException)

        where:
        scenario|values
        1|null
        2|new String[0]

    }
}
