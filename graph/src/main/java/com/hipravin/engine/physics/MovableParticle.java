package com.hipravin.engine.physics;

import com.hipravin.engine.math.Point2d;
import com.hipravin.engine.math.Vector2d;

public class MovableParticle {
    private Point2d location;
    private Vector2d speed;

    public MovableParticle(Point2d location, Vector2d speed) {
        this.location = location;
        this.speed = speed;
    }

    public Point2d getLocation() {
        return location;
    }

    public void setLocation(Point2d location) {
        this.location = location;
    }

    public Vector2d getSpeed() {
        return speed;
    }

    public void setSpeed(Vector2d speed) {
        this.speed = speed;
    }

}
