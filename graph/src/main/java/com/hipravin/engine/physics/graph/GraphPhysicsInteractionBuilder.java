package com.hipravin.engine.physics.graph;

import com.hipravin.engine.math.Vector2d;
import com.hipravin.engine.math.VectorMath;
import com.hipravin.engine.physics.Force;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.hipravin.engine.physics.graph.GraphPhysicParams.EDGE_REPULSION_BOUNDARY;

public class GraphPhysicsInteractionBuilder {
    private List<SingleParticleGenericPhysicRule> singleParticleRules = new ArrayList<>();
    private List<TwoParticleGenericPhysicRule> twoParticlePhysicRules = new ArrayList<>();
    private List<TwoParticleGenericPhysicRule> twoParticleOnlyClosestRules = new ArrayList<>();
    private List<LinkedParticlesPhysicRule> linkedParticlesPhysicRules = new ArrayList<>();

    public GraphPhysicsInteractionBuilder withMediumViscosity() {
        singleParticleRules.add(particle -> {
            Vector2d minusSpeed = VectorMath.negate(particle.getSpeed());
            double speed = VectorMath.lenght(particle.getSpeed());
            Vector2d f = VectorMath.mul(minusSpeed, speed * GraphPhysicParams.VISCOSITY_COEFF);

            return new Force(f);
        });

        return this;
    }

    public GraphPhysicsInteractionBuilder withLinkGravity() {
        linkedParticlesPhysicRules.add(l -> {
            Vector2d location12 = VectorMath.vectorBetweenPoints(l.getParticleFrom().getLocation(), l.getParticleTo().getLocation());
            double lenDelta = VectorMath.lenght(location12) - GraphPhysicParams.LINK_LEN;
            if (lenDelta > 0) {
                Vector2d f1 = VectorMath.mul(location12, GraphPhysicParams.LINK_GUK_COEFF * lenDelta * l.getWeight());
                Vector2d f2 = VectorMath.negate(f1);

                return Map.of(
                        l.getParticleFrom(), new Force(f1),
                        l.getParticleTo(), new Force(f2));
            } else {
                return Collections.emptyMap();
            }
        });

//        return this.withExplicitRule(() -> Map.of(from, ));
        return this;
    }

    public GraphPhysicsInteractionBuilder withExplicitRule(LinkedParticlesPhysicRule explicitRule) {
        linkedParticlesPhysicRules.add(explicitRule);
        return this;
    }

    public GraphPhysicsInteractionBuilder withEdgeRepulsion() {
        return this.withEdgeRepulsionAcceleration(
                GraphPhysicParams.X_EDGE_MIN, GraphPhysicParams.X_EDGE_MAX, GraphPhysicParams.Y_EDGE_MIN, GraphPhysicParams.Y_EDGE_MAX,
                distance -> GraphPhysicParams.EDGE_REPULSION_COEFF / (distance * distance * distance));//cube
    }

    public GraphPhysicsInteractionBuilder withPairwiseSquareRepulsion() {
        twoParticlePhysicRules.add((p1, p2) -> {
            Vector2d location12 = VectorMath.vectorBetweenPoints(p1.getLocation(), p2.getLocation());
            double distance = VectorMath.lenght(location12);

            if (distance < GraphPhysicParams.ZERO_DOUBLE) {
                return Collections.emptyMap();
            } else {
                double f = p1.getMass() * p2.getMass() * GraphPhysicParams.PAIRWISE_REPULSION_COEFF / (distance * distance);

                Vector2d vf1 = VectorMath.mul(VectorMath.normalize(location12), f);

                return Map.of(
                        p1, new Force(VectorMath.negate(vf1)),
                        p2, new Force(vf1));
            }
        });

        return this;
    }

    /**
     * @param repulsionAcceleration acceleration that increases when particle gets closer to the edge
     */
    GraphPhysicsInteractionBuilder withEdgeRepulsionAcceleration(double xleft, double xright, double ymin, double ymax, Function<Double, Double> repulsionAcceleration) {
        //left-right
        singleParticleRules.add(particle -> {
            double toLeftEdge = particle.getLocation().getX() - xleft;
            double fromRightEdge = particle.getLocation().getX() - xright;
            if (toLeftEdge > 0 && fromRightEdge < 0
                    && Math.min(toLeftEdge, -fromRightEdge) < (xright - xleft) * EDGE_REPULSION_BOUNDARY
            ) {
                return (toLeftEdge < -fromRightEdge)
                        ? new Force(new Vector2d(particle.getMass() * repulsionAcceleration.apply(toLeftEdge), 0))
                        : new Force(new Vector2d(-particle.getMass() * repulsionAcceleration.apply(-fromRightEdge), 0));
            } else {
                return new Force(new Vector2d(0, 0));
            }
        });
        //top-bottom
        singleParticleRules.add(particle -> {
            double toTopEdge = particle.getLocation().getY() - ymin;
            double fromBottomEdge = particle.getLocation().getY() - ymax;
            if (toTopEdge > 0 && fromBottomEdge < 0
                    && Math.min(toTopEdge, -fromBottomEdge) < (ymax - ymin) * EDGE_REPULSION_BOUNDARY
            ) {
                return (toTopEdge < -fromBottomEdge)
                        ? new Force(new Vector2d(0, particle.getMass() * repulsionAcceleration.apply(toTopEdge)))
                        : new Force(new Vector2d(0, -particle.getMass() * repulsionAcceleration.apply(-fromBottomEdge)));
            } else {
                return new Force(new Vector2d(0, 0));
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
        return new GraphPhysicRulesCompositionImpl(singleParticleRules, twoParticlePhysicRules, linkedParticlesPhysicRules);
    }
}
