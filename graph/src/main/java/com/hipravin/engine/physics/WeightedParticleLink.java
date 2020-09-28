package com.hipravin.engine.physics;

public class WeightedParticleLink {
    private final MassiveMovableParticle particleFrom;
    private final MassiveMovableParticle particleTo;
    private final double weight;

    public WeightedParticleLink(MassiveMovableParticle particleFrom, MassiveMovableParticle particleTo, double weight) {
        this.particleFrom = particleFrom;
        this.particleTo = particleTo;
        this.weight = weight;
    }

    public MassiveMovableParticle getParticleFrom() {
        return particleFrom;
    }

    public MassiveMovableParticle getParticleTo() {
        return particleTo;
    }

    public double getWeight() {
        return weight;
    }
}
