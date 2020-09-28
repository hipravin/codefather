package com.hipravin.engine.physics.graph;

import com.hipravin.engine.physics.Force;
import com.hipravin.engine.physics.MassiveMovableParticle;
import com.hipravin.engine.physics.WeightedParticleLink;

import java.util.Map;

@FunctionalInterface
public interface LinkedParticlesPhysicRule {
    Map<MassiveMovableParticle, Force> computeForceComponents(WeightedParticleLink weightedParticleLink);
}
