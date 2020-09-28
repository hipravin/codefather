package com.hipravin.engine.physics;

import java.util.stream.Stream;

public interface MovableParticleSystem {
    void tick(double dt);
    Stream<MassiveMovableParticle> getObjects();
}
