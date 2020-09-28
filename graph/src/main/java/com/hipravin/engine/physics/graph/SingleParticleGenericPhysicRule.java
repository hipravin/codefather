package com.hipravin.engine.physics.graph;

import com.hipravin.engine.physics.Force;
import com.hipravin.engine.physics.MassiveMovableParticle;

/**
 * Defines forces irrespective of other particles in the system
 */
@FunctionalInterface
public interface SingleParticleGenericPhysicRule {
    Force computeForceComponent(MassiveMovableParticle particle);
}
