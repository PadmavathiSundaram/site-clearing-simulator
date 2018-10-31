package com.mylearning.model;

import com.mylearning.constants.Land;
import com.mylearning.constants.Orientation;

public class Square {
    private Coordinate coordinate = new Coordinate(0, 0);
    private Boolean isCleared = false;
    private Land land = Land.o;
    private Orientation facingTo = Orientation.FRONT;

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public Land getLand() {
        return land;
    }

    public void setLand(Land land) {
        this.land = land;
    }

    public Orientation getFacingTo() {
        return facingTo;
    }

    public void setFacingTo(Orientation facingTo) {
        this.facingTo = facingTo;
    }

    public boolean getIsCleared() {
        return this.isCleared;
    }

    public void setIsCleared(boolean isCleared) {
        this.isCleared = isCleared;
    }
}
