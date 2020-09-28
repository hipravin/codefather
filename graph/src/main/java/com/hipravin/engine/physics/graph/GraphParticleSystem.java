package com.hipravin.engine.physics.graph;

import com.hipravin.engine.math.Point2d;
import com.hipravin.engine.math.Vector2d;
import com.hipravin.engine.model.Graph;
import com.hipravin.engine.model.GraphLink;
import com.hipravin.engine.model.GraphNode;
import com.hipravin.engine.physics.*;

import java.util.*;
import java.util.stream.Stream;

public class GraphParticleSystem implements MovableParticleSystem {

    private GraphPhysicsRules graphPhysicsRules;
    private List<MassiveMovableParticle> particles = new ArrayList<>();
    private List<WeightedParticleLink> links = new ArrayList<>();
    private Map<GraphNode, MassiveMovableParticle> nodeToParticle = new IdentityHashMap<>();

    GraphParticleSystem() {

    }

    public static GraphParticleSystem initFromGraphWithRandomLocations(Graph graph, GraphPhysicsRules graphPhysicsRules) {
        Random random = new Random(0);
        GraphParticleSystem graphParticleSystem = new GraphParticleSystem();
        graphParticleSystem.setGraphPhysicsRules(graphPhysicsRules);


        graph.getNodes().forEach(node -> {
            MassiveMovableParticle particle = new MassiveMovableParticle(randomLocation(random), node.getWeight());
            graphParticleSystem.particles.add(particle);
            graphParticleSystem.nodeToParticle.put(node, particle);
        });
        graph.getNodes().forEach(node -> {
            node.getLinks().forEach(graphLink -> {
                MassiveMovableParticle from = graphParticleSystem.nodeToParticle.get(node);
                MassiveMovableParticle to = graphParticleSystem.nodeToParticle.get(graphLink.getTo());

                graphParticleSystem.links.add(new WeightedParticleLink(from, to, graphLink.getWeight()));
            });
        });

        return graphParticleSystem;
    }

    @Override
    public void tick(double dt) {
        Map<MassiveMovableParticle, Force> preTickForces = graphPhysicsRules.computeForces(particles, links);

        preTickForces.forEach((p, f) -> {
            Vector2d acceleration = NewtonPhysics.computeAcceleration(p, f);
            Vector2d newSpeed = NewtonPhysics.accelerate(p.getSpeed(), acceleration, dt);
            Point2d newPosition = NewtonPhysics.move(p.getLocation(), newSpeed, dt);

            p.setLocation(newPosition);
            p.setSpeed(newSpeed);
        });
    }

    private static Point2d randomLocation(Random random) {
        return randomLocation(random, GraphPhysicParams.X_MIN_INIT, GraphPhysicParams.X_MAX_INIT, GraphPhysicParams.Y_MIN_INIT, GraphPhysicParams.Y_MAX_INIT);
    }

    private static Point2d randomLocation(Random random, double xmin, double xmax, double ymin, double ymax) {
        double xp = random.nextDouble();
        double yp = random.nextDouble();

        double x = xmin + (xmax - xmin) * xp;
        double y = ymin + (ymax - ymin) * yp;

        return new Point2d(x, y);
    }

    @Override
    public Stream<MassiveMovableParticle> getObjects() {
        return particles.stream();
    }

    public Point2d getGraphNodeLocation(GraphNode graphNode) {
        return nodeToParticle.get(graphNode).getLocation();
    }

    public GraphPhysicsRules getGraphPhysicsRules() {
        return graphPhysicsRules;
    }

    public void setGraphPhysicsRules(GraphPhysicsRules graphPhysicsRules) {
        this.graphPhysicsRules = graphPhysicsRules;
    }

    public Map<GraphNode, MassiveMovableParticle> getNodeToParticle() {
        return nodeToParticle;
    }
}
