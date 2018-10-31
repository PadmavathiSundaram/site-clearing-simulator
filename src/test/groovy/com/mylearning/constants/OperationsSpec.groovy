package com.mylearning.constants

import com.mylearning.strategy.impl.AdvanceNavigationStrategyImpl
import com.mylearning.strategy.impl.LeftNavigationStrategyImpl
import com.mylearning.constants.Operations
import com.mylearning.strategy.impl.QuitStrategyImpl
import com.mylearning.strategy.impl.RightNavigationStrategyImpl
import spock.lang.Unroll

class OperationsSpec extends spock.lang.Specification {
    @Unroll
    def "model.OperationsSpec check if right Execution com.mylearning.strategy invoved based on operation type"() {
        when:
        def result = Operations.getIfPresent(operation)

        then:
        result == expectedOperation
        navigationStrategy.isInstance(result.getNavigationStrategy())

        where:
        scenario | operation | expectedOperation | navigationStrategy
        1        | "advance" | Operations.A      | AdvanceNavigationStrategyImpl.class
        2        | "A"       | Operations.A      | AdvanceNavigationStrategyImpl.class
        3        | "a"       | Operations.A      | AdvanceNavigationStrategyImpl.class
        4        | "Advance" | Operations.A      | AdvanceNavigationStrategyImpl.class
        5        | "ADVANCE" | Operations.A      | AdvanceNavigationStrategyImpl.class

        6        | "left"    | Operations.L      | LeftNavigationStrategyImpl.class
        7        | "Left"    | Operations.L      | LeftNavigationStrategyImpl.class
        8        | "l"       | Operations.L      | LeftNavigationStrategyImpl.class
        9        | "L"       | Operations.L      | LeftNavigationStrategyImpl.class
        10       | "lEFT"    | Operations.L      | LeftNavigationStrategyImpl.class

        11       | "right"   | Operations.R      | RightNavigationStrategyImpl.class
        12       | "r"       | Operations.R      | RightNavigationStrategyImpl.class
        13       | "R"       | Operations.R      | RightNavigationStrategyImpl.class
        14       | "Right"   | Operations.R      | RightNavigationStrategyImpl.class
        15       | "RIGHT"   | Operations.R      | RightNavigationStrategyImpl.class

        16       | "Quit"    | Operations.Q      | QuitStrategyImpl.class
        17       | "QUIT"    | Operations.Q      | QuitStrategyImpl.class
        16       | "Q"       | Operations.Q      | QuitStrategyImpl.class
        17       | "q"       | Operations.Q      | QuitStrategyImpl.class
        16       | "quit"    | Operations.Q      | QuitStrategyImpl.class


    }

    @Unroll
    def "model.OperationsSpec check for invalid operations data"() {
        when:
        def result = Operations.getIfPresent(operation)

        then:
        result == null


        where:
        scenario | operation
        1        | null
        2        | ""
        3        | " "
        4        | "4534"
        5        | "junk"
        6        | "abc "


    }
}
