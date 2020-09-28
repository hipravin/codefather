package com.hipravin.engine.physics.graph;

import com.hipravin.engine.physics.Force;
import com.hipravin.engine.physics.MassiveMovableParticle;

import java.util.Collection;
import java.util.Map;

/**
 * Definition of forces between particles, e.g. gravity.
 */
@FunctionalInterface
public interface TwoParticleGenericPhysicRule {
    Map<MassiveMovableParticle, Force> computeForceComponents(MassiveMovableParticle particle1, MassiveMovableParticle particle2);
}
