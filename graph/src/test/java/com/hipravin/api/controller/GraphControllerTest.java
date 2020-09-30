package com.hipravin.api.controller;

import com.hipravin.api.model.GraphAnimationDto;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.time.Duration;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class GraphControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    TestRestTemplate testRestTemplate;

    @Test
    void testSimulation() {
        String graphId = "sample-r100";
        int iterations = 10;

        Instant start = Instant.now();

        for (int tick = 0; tick < iterations; tick++) {
            String url = "http://localhost:" + port + "/api/v1/graph/" + graphId + "/simulation/" + tick;
            ResponseEntity<GraphAnimationDto> simulationResponse = testRestTemplate.getForEntity(url, GraphAnimationDto.class);

            assertNotNull(simulationResponse.getBody());
            assertEquals(100, simulationResponse.getBody().getGraph().getNodes().size());
        }
        System.out.println("Total: " + Duration.between(start, Instant.now()));
        System.out.println("Average: " + Duration.between(start, Instant.now()).dividedBy(iterations));

    }
}