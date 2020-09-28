package com.hipravin.engine.physics.graph;

import com.hipravin.engine.physics.Force;
import com.hipravin.engine.physics.MassiveMovableParticle;
import com.hipravin.engine.physics.NewtonPhysics;
import com.hipravin.engine.physics.WeightedParticleLink;

import java.util.Collection;
import java.util.IdentityHashMap;
import java.util.List;
import java.util.Map;

public class GraphPhysicRulesCompositionImpl implements GraphPhysicsRules {
    private List<SingleParticleGenericPhysicRule> singleParticleRules;
    private List<TwoParticleGenericPhysicRule> twoParticlePhysicRules;
    private List<LinkedParticlesPhysicRule> linkedParticlesRules;

    public GraphPhysicRulesCompositionImpl(List<SingleParticleGenericPhysicRule> singleParticleRules,
                                           List<TwoParticleGenericPhysicRule> twoParticlePhysicRules,
                                           List<LinkedParticlesPhysicRule> linkedParticlesPhysicRules) {
        this.singleParticleRules = singleParticleRules;
        this.twoParticlePhysicRules = twoParticlePhysicRules;
        this.linkedParticlesRules = linkedParticlesPhysicRules;
    }

    @Override
    public Map<MassiveMovableParticle, Force> computeForces(Collection<MassiveMovableParticle> particles, Collection<WeightedParticleLink> links) {
        Map<MassiveMovableParticle, Force> forcesSummarized = new IdentityHashMap<>();

        //single particle
        for (MassiveMovableParticle particle : particles) {
            for (SingleParticleGenericPhysicRule singleParticleRule : singleParticleRules) {
                Force f = singleParticleRule.computeForceComponent(particle);
                forcesSummarized.merge(particle, f, (f1,f2) -> NewtonPhysics.sumOfForces(f1,f2));
            }
        }
        //two particles
        //this might require optimization if particle count if greater than 100
        //however such amount is hard to display
        for (MassiveMovableParticle particle1 : particles) {
            for (MassiveMovableParticle particle2 : particles) {
                if(particle1 != particle2) {
                    for (TwoParticleGenericPhysicRule twoParticlePhysicRule : twoParticlePhysicRules) {
                        mergeForcesMaps(forcesSummarized, twoParticlePhysicRule.computeForceComponents(particle1, particle2));
                    }
                }
            }
        }
        //link rules
        for (LinkedParticlesPhysicRule linkedParticlesRule : linkedParticlesRules) {
            for (WeightedParticleLink weightedParticleLink : links) {
                mergeForcesMaps(forcesSummarized, linkedParticlesRule.computeForceComponents(weightedParticleLink));
            }
        }

        return forcesSummarized;
    }

    private static void mergeForcesMaps(Map<MassiveMovableParticle, Force> forcesSummarized, Map<MassiveMovableParticle, Force> forces) {
        forces.forEach((particle, force) -> {
            forcesSummarized.merge(particle, force,  (f1, f2) -> NewtonPhysics.sumOfForces(f1, f2));
        });
    }

    public List<SingleParticleGenericPhysicRule> getSingleParticleRules() {
        return singleParticleRules;
    }

    public void setSingleParticleRules(List<SingleParticleGenericPhysicRule> singleParticleRules) {
        this.singleParticleRules = singleParticleRules;
    }

    public List<TwoParticleGenericPhysicRule> getTwoParticlePhysicRules() {
        return twoParticlePhysicRules;
    }

    public void setTwoParticlePhysicRules(List<TwoParticleGenericPhysicRule> twoParticlePhysicRules) {
        this.twoParticlePhysicRules = twoParticlePhysicRules;
    }

    public List<LinkedParticlesPhysicRule> getLinkedParticlesRules() {
        return linkedParticlesRules;
    }

    public void setLinkedParticlesRules(List<LinkedParticlesPhysicRule> linkedParticlesRules) {
        this.linkedParticlesRules = linkedParticlesRules;
    }
}
