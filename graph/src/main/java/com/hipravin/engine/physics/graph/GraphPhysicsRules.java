package com.hipravin.engine.physics.graph;

import com.hipravin.engine.physics.Force;
import com.hipravin.engine.physics.MassiveMovableParticle;
import com.hipravin.engine.physics.WeightedParticleLink;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface GraphPhysicsRules {
    Map<MassiveMovableParticle, Force> computeForces(List<MassiveMovableParticle> particles,
                                                     List<WeightedParticleLink> links);
}
