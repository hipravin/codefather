package com.hipravin.engine.physics;

import com.hipravin.engine.math.Point2d;
import com.hipravin.engine.math.Vector2d;
import com.hipravin.engine.math.VectorMath;

import java.util.Arrays;

import static com.hipravin.engine.math.VectorMath.*;

public final class NewtonPhysics {
    private NewtonPhysics() {}

    public static Vector2d accelerate(Vector2d speed, Vector2d acc, double t) {
        return VectorMath.sum(speed, VectorMath.mul(acc, t));
    }

    public static Point2d move(Point2d p, Vector2d speed, double t) {
        return VectorMath.move(p, mul(speed, t));
    }

    public static Vector2d computeAcceleration(MassiveMovableParticle particle, Force force) {
        return VectorMath.divide(force.getValue(), particle.getMass());
    }

    public static Force sumOfForces(Force... forces) {
        Vector2d summarizedVector = Arrays.stream(forces).map(Force::getValue)
                .reduce(VectorMath::sum).orElse(ZERO_VECTOR);

        return new Force(summarizedVector);
    }

    public static Force sumOfForces(Force f1, Force f2) {
        Vector2d summarizedVector = VectorMath.sum(f1.getValue(), f2.getValue());

        return new Force(summarizedVector);
    }

    public static Force negateForceVector(Force force) {
        return new Force(VectorMath.negate(force.getValue()));
    }
}
