package com.mylearning.constants;

import com.mylearning.model.Coordinate;

import java.util.HashMap;
import java.util.Map;

/**
 * Orientations that aid in Navigation of the Site Map
 */
public enum Orientation {
    FRONT("front") {
        @Override
        public Coordinate getNextCoordinates(Coordinate coordinate) {
            return calculateNextCoordinate(coordinate, 0, 1);
        }

        @Override
        public int getOrientationIndex() {
            return 0;
        }
    },
    DOWN("down") {
        @Override
        public Coordinate getNextCoordinates(Coordinate coordinate) {
            return calculateNextCoordinate(coordinate, 1, 0);
        }

        @Override
        public int getOrientationIndex() {
            return 1;
        }
    },
    BACK("back") {
        @Override
        public Coordinate getNextCoordinates(Coordinate coordinate) {
            return calculateNextCoordinate(coordinate, 0, -1);
        }

        @Override
        public int getOrientationIndex() {
            return 2;
        }
    },
    UP("up") {
        @Override
        public Coordinate getNextCoordinates(Coordinate coordinate) {
            return calculateNextCoordinate(coordinate, -1, 0);
        }

        @Override
        public int getOrientationIndex() {
            return 3;
        }
    };

    public String getOrientation() {
        return orientation;
    }

    private String orientation;

    Orientation(String orientation) {
        this.orientation = orientation;
    }

    public static final Map<Integer, Orientation> orientationIndex = new HashMap<Integer, Orientation>();

    static {
        int index = 0;
        for (Orientation value : Orientation.values()) {
            orientationIndex.put(index, value);
            index++;
        }
    }

    public int getOrientationIndex() {
        return 0;
    }

    public Coordinate getNextCoordinates(Coordinate coordinate) {
        return calculateNextCoordinate(coordinate, 0, 1);
    }

    Coordinate calculateNextCoordinate(Coordinate coordinate, int x, int y) {
        Coordinate nextCoordinate = new Coordinate(coordinate.getX() + x, coordinate.getY() + y);
        return nextCoordinate;
    }

}
