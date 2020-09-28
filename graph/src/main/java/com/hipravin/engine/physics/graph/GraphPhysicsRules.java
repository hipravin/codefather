package com.hipravin.engine.physics.graph;

import com.hipravin.engine.physics.Force;
import com.hipravin.engine.physics.MassiveMovableParticle;
import com.hipravin.engine.physics.WeightedParticleLink;

import java.util.Collection;
import java.util.Map;

public interface GraphPhysicsRules {
    Map<MassiveMovableParticle, Force> computeForces(Collection<MassiveMovableParticle> particles,
                                                     Collection<WeightedParticleLink> links);
}
