package com.hipravin.engine;

import com.hipravin.api.model.GraphAnimationDto;
import com.hipravin.engine.physics.graph.GraphParticleSystem;
import com.hipravin.engine.physics.graph.GraphPhysicParams;
import com.hipravin.engine.physics.graph.GraphPhysicSimulation;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class GraphSimulationServiceTest {
    @Autowired
    GraphSimulationService graphSimulationService;

    @Test
    void testSimulate() {
//        String graphId = "sample-chess";
//        String graphId = "sample-r120";
        String graphId = "sample-spring-core";
        int iterations = 100;

        Instant start = Instant.now();

        GraphPhysicSimulation simulation = graphSimulationService.buildSimulationById(graphId);

        for (int tick = 0; tick < iterations; tick++) {
            GraphAnimationDto dto = graphSimulationService.advanceSimulationToTick(simulation, tick);
            ensureWithinEdges(dto, tick);
        }
        System.out.println("Total: " + Duration.between(start, Instant.now()));
        System.out.println("Average: " + Duration.between(start, Instant.now()).dividedBy(iterations));

    }

    void ensureWithinEdges(GraphAnimationDto dto, int tick) {
        dto.getGraph().getNodes().forEach(n -> {
            assertTrue(n.getPosition().getX() >= GraphPhysicParams.X_MIN, () -> tick + ": " + n.getPosition().getX());
            assertTrue(n.getPosition().getX() <= GraphPhysicParams.X_MAX, () -> tick + ": " + n.getPosition().getX());
            assertTrue(n.getPosition().getY() >= GraphPhysicParams.Y_MIN, () -> tick + ": " + n.getPosition().getY());
            assertTrue(n.getPosition().getY() <= GraphPhysicParams.Y_MAX, () -> tick + ": " + n.getPosition().getY());
        });
    }
}