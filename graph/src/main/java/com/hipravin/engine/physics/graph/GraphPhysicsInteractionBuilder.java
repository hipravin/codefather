package com.hipravin.engine.physics.graph;

import com.hipravin.engine.math.Vector2d;
import com.hipravin.engine.physics.Force;
import com.hipravin.engine.physics.MassiveMovableParticle;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class GraphPhysicsInteractionBuilder {
    private List<SingleParticleGenericPhysicRule> singleParticleRules = new ArrayList<>();
    private List<TwoParticleGenericPhysicRule> twoParticlePhysicRules = new ArrayList<>();
    private List<LinkedParticlesPhysicRule> explicitPhysicRules = new ArrayList<>();

    public GraphPhysicsInteractionBuilder withLinkGravity(MassiveMovableParticle from, MassiveMovableParticle to, double weight) {


//        return this.withExplicitRule(() -> Map.of(from, ));
        return this;
    }

    public GraphPhysicsInteractionBuilder withExplicitRule(LinkedParticlesPhysicRule explicitRule) {
        explicitPhysicRules.add(explicitRule);
        return this;
    }

    public GraphPhysicsInteractionBuilder withEdgeSquareRepulsion() {
        return this.withEdgeRepulsionAcceleration(
                GraphPhysicParams.X_MIN, GraphPhysicParams.X_MAX, GraphPhysicParams.Y_MIN, GraphPhysicParams.Y_MAX,
                 distance -> GraphPhysicParams.EDGE_REPULSION_COEFF / (distance * distance));
    }

    /**
     *
     * @param repulsionAcceleration acceleration that increases when particle gets closer to the edge
     */
    GraphPhysicsInteractionBuilder withEdgeRepulsionAcceleration(double xleft, double xright, double ymin, double ymax, Function<Double, Double> repulsionAcceleration) {
        //left
        singleParticleRules.add(particle -> {
            double toLeftEdge = particle.getLocation().getX() - xleft;
            if(toLeftEdge > 0) {
                double dx = particle.getMass() * repulsionAcceleration.apply(toLeftEdge);

                return new Force(new Vector2d(dx, 0));
            } else {
                return new Force(new Vector2d(0, 0 ));
            }
        });
        //right
        singleParticleRules.add(particle -> {
            double toRightEdge = xright - particle.getLocation().getX();
            if(toRightEdge > 0) {
                double dx = particle.getMass() * repulsionAcceleration.apply(toRightEdge);

                return new Force(new Vector2d(-dx, 0));
            } else {
                return new Force(new Vector2d(0, 0 ));
            }
        });
        //top
        singleParticleRules.add(particle -> {
            double toTopEdge = particle.getLocation().getY() - ymin;
            if(toTopEdge > 0) {
                double dy = particle.getMass() * repulsionAcceleration.apply(toTopEdge);

                return new Force(new Vector2d(0, dy));
            } else {
                return new Force(new Vector2d(0, 0 ));
            }
        });
        //bottom
        singleParticleRules.add(particle -> {
            double toBottomEdge = ymax - particle.getLocation().getY();
            if(toBottomEdge > 0) {
                double dy = particle.getMass() * repulsionAcceleration.apply(toBottomEdge);

                return new Force(new Vector2d(0, -dy));
            } else {
                return new Force(new Vector2d(0, 0 ));
            }
        });


        return this;
    }

    public GraphPhysicsInteractionBuilder withRule(SingleParticleGenericPhysicRule rule) {
        singleParticleRules.add(rule);
        return this;
    }

    public GraphPhysicsInteractionBuilder withRule(TwoParticleGenericPhysicRule rule) {
        twoParticlePhysicRules.add(rule);
        return this;
    }

    public GraphPhysicRulesCompositionImpl build() {
        return new GraphPhysicRulesCompositionImpl(singleParticleRules, twoParticlePhysicRules, explicitPhysicRules);
    }
}
