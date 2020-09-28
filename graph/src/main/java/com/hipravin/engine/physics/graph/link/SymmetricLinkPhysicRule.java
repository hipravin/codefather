package com.hipravin.engine.physics.graph.link;

import com.hipravin.engine.math.Vector2d;
import com.hipravin.engine.math.VectorMath;
import com.hipravin.engine.physics.Force;
import com.hipravin.engine.physics.MassiveMovableParticle;
import com.hipravin.engine.physics.WeightedParticleLink;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * vector F12 = - F21 (3-rd Newton's law)
 */
public class SymmetricLinkPhysicRule extends LinkPhysicRule {
    private final BiFunction<MassiveMovableParticle, MassiveMovableParticle, Double> forceModuleCalculator;
//
    public SymmetricLinkPhysicRule(MassiveMovableParticle particleFrom, MassiveMovableParticle particleTo,
                                   BiFunction<MassiveMovableParticle, MassiveMovableParticle, Double> forceModuleCalculator) {
        super(particleFrom, particleTo);
        this.forceModuleCalculator = forceModuleCalculator;
    }
//
//    @Override
//    public Map<MassiveMovableParticle, Force> computeForceComponents() {
//        double fmodule = forceModuleCalculator.apply(getParticleFrom(), getParticleTo());
//        Vector2d location12 = VectorMath.vectorBetweenPoints(getParticleTo().getLocation(), getParticleFrom().getLocation());
//
//        Vector2d f1 = VectorMath.mul(VectorMath.normalize(location12), fmodule);
//        Vector2d f2 = VectorMath.negate(f1);
//
//        return Map.of(getParticleFrom(), new Force(f1),
//                getParticleTo(), new Force(f2));
//
//    }


    @Override
    public Map<MassiveMovableParticle, Force> computeForceComponents(WeightedParticleLink weightedParticleLink) {
        return null;
    }
}
