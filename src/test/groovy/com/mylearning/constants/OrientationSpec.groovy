package com.mylearning.constants

import com.mylearning.model.Coordinate
import com.mylearning.constants.Orientation
import spock.lang.Unroll;

class OrientationSpec extends spock.lang.Specification {

    @Unroll
    def "DirectionsSpec check for next coordinate in given direction"() {
        given:
        def coordinate = new Coordinate(x, y)

        when:
        def result = orientation.getNextCoordinates(coordinate)

        then:
        result.x == expectedX
        result.y == expectedY

        where:
        scenario | orientation       | x | y | expectedX | expectedY
        1        | Orientation.FRONT | 0 | 0 | 0         | 1
        2        | Orientation.BACK  | 0 | 1 | 0         | 0
        3        | Orientation.UP    | 1 | 1 | 0         | 1
        4        | Orientation.DOWN  | 0 | 0 | 1         | 0
        5        | Orientation.FRONT | 1 | 1 | 1         | 2
        6        | Orientation.BACK  | 1 | 1 | 1         | 0
        7        | Orientation.UP    | 1 | 1 | 0         | 1
        8        | Orientation.DOWN  | 1 | 1 | 2         | 1
    }
}
