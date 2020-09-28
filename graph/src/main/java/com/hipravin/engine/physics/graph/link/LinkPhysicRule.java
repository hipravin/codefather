package com.hipravin.engine.physics.graph.link;

import com.hipravin.engine.physics.MassiveMovableParticle;
import com.hipravin.engine.physics.graph.LinkedParticlesPhysicRule;

/**
 * Rule reasoned by a link between two nodes in graph
 */
public abstract class LinkPhysicRule implements LinkedParticlesPhysicRule {
    private final MassiveMovableParticle particleFrom;
    private final MassiveMovableParticle particleTo;

    public LinkPhysicRule(MassiveMovableParticle particleFrom, MassiveMovableParticle particleTo) {
        this.particleFrom = particleFrom;
        this.particleTo = particleTo;
    }

    public MassiveMovableParticle getParticleFrom() {
        return particleFrom;
    }

    public MassiveMovableParticle getParticleTo() {
        return particleTo;
    }
}
