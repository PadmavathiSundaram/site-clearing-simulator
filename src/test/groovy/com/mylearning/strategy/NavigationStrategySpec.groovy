package com.mylearning.strategy

import com.mylearning.constants.Operations
import com.mylearning.constants.Orientation
import com.mylearning.exception.FatalException
import com.mylearning.model.Coordinate
import com.mylearning.model.Simulator
import com.mylearning.model.Square
import spock.lang.Unroll

class NavigationStrategySpec extends spock.lang.Specification {

    def "NavigationStrategySpec check for handling First Advance Operations"() {
        given:
        Simulator.currentSquare = null

        Simulator.squares = new Square[10][10];
        Simulator.squares[0][0] = new Square();

        when:
        Operations.A.getNavigationStrategy().navigate()
        def result = Simulator.currentSquare

        then:
        result.getCoordinate().getX() == 0
        result.getCoordinate().getY() == 0
    }

    def "NavigationStrategySpec check for handling beyond siteMap Advance Operations"() {
        given:

        Simulator.squares = new Square[1][1];
        Simulator.currentSquare = new Square(coordinate: new Coordinate(2, 2));

        when:
        Operations.A.getNavigationStrategy().navigate()
        def result = Simulator.currentSquare

        then:
        thrown(FatalException)
    }

    @Unroll
    def "NavigationStrategySpec check for next orientation for the given operation"() {
        given:
        def coordinate = new Coordinate(x, y)
        def square = new Square();
        square.setCoordinate(coordinate);
        square.setFacingTo(orientation)
        Simulator.currentSquare = square
        Simulator.squares = new Square[10][10];
        Simulator.squares[expectedX][expectedY] = new Square(coordinate: new Coordinate(expectedX, expectedY))

        when:
        operation.getNavigationStrategy().navigate()
        def result = Simulator.currentSquare

        then:
        result.getFacingTo() == expectedOrientation
        result.getCoordinate().getX() == expectedX
        result.getCoordinate().getY() == expectedY

        where:
        scenario | orientation       | operation    | x | y | expectedX | expectedY | expectedOrientation
        1        | Orientation.FRONT | Operations.A | 1 | 1 | 1         | 2         | Orientation.FRONT
        2        | Orientation.BACK  | Operations.A | 1 | 1 | 1         | 0         | Orientation.BACK
        3        | Orientation.UP    | Operations.A | 1 | 1 | 0         | 1         | Orientation.UP
        4        | Orientation.DOWN  | Operations.A | 1 | 1 | 2         | 1         | Orientation.DOWN

        5        | Orientation.FRONT | Operations.R | 1 | 1 | 1         | 1         | Orientation.DOWN
        6        | Orientation.BACK  | Operations.R | 1 | 1 | 1         | 1         | Orientation.UP
        7        | Orientation.UP    | Operations.R | 1 | 1 | 1         | 1         | Orientation.FRONT
        8        | Orientation.DOWN  | Operations.R | 1 | 1 | 1         | 1         | Orientation.BACK

        9        | Orientation.FRONT | Operations.L | 1 | 1 | 1         | 1         | Orientation.UP
        10       | Orientation.BACK  | Operations.L | 1 | 1 | 1         | 1         | Orientation.DOWN
        11       | Orientation.UP    | Operations.L | 1 | 1 | 1         | 1         | Orientation.BACK
        12       | Orientation.DOWN  | Operations.L | 1 | 1 | 1         | 1         | Orientation.FRONT

    }


    @Unroll
    def "NavigationStrategySpec check for next coordinate for the given operation + advance"() {
        given:
        def coordinate = new Coordinate(x, y)
        def square = new Square();
        square.setCoordinate(coordinate);
        square.setFacingTo(orientation)
        Simulator.currentSquare = square
        Simulator.squares = new Square[10][10];
        Simulator.squares[expectedX][expectedY] = new Square(coordinate: new Coordinate(expectedX, expectedY))

        when:
        operation.getNavigationStrategy().navigate()
        Operations.A.getNavigationStrategy().navigate()
        def result = Simulator.currentSquare

        then:
        result.getFacingTo() == expectedOrientation
        result.getCoordinate().getX() == expectedX
        result.getCoordinate().getY() == expectedY

        where:
        scenario | orientation       | operation    | x | y | expectedX | expectedY | expectedOrientation

        1        | Orientation.FRONT | Operations.R | 1 | 1 | 2         | 1         | Orientation.DOWN
        2        | Orientation.BACK  | Operations.R | 1 | 1 | 0         | 1         | Orientation.UP
        3        | Orientation.UP    | Operations.R | 1 | 1 | 1         | 2         | Orientation.FRONT
        4        | Orientation.DOWN  | Operations.R | 1 | 1 | 1         | 0         | Orientation.BACK

        5        | Orientation.FRONT | Operations.L | 1 | 1 | 0         | 1         | Orientation.UP
        6        | Orientation.BACK  | Operations.L | 1 | 1 | 2         | 1         | Orientation.DOWN
        7        | Orientation.UP    | Operations.L | 1 | 1 | 1         | 0         | Orientation.BACK
        8        | Orientation.DOWN  | Operations.L | 1 | 1 | 1         | 2         | Orientation.FRONT

    }
}
