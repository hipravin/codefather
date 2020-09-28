package com.hipravin.engine.physics;

import com.hipravin.engine.math.Point2d;
import com.hipravin.engine.math.Vector2d;

public class MassiveMovableParticle extends MovableParticle {
    private double mass = 1.0d;

    public MassiveMovableParticle(Point2d location, Vector2d speed) {
        super(location, speed);
    }

    public MassiveMovableParticle(Point2d location, double mass) {
        super(location, new Vector2d(0,0));
        this.mass = mass;
    }

    public MassiveMovableParticle(Point2d location, Vector2d speed, double mass) {
        super(location, speed);
        this.mass = mass;
    }

    public double getMass() {
        return mass;
    }

    public void setMass(double mass) {
        this.mass = mass;
    }
}
