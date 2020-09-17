package com.hipravin.api.model;

public class PositionDto {
    private double x;
    private double y;

    public static PositionDto of(double x, double y) {
        return  new PositionDto(x, y);
    }

    PositionDto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    PositionDto() {
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
