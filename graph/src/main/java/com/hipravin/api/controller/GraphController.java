package com.hipravin.api.controller;

import com.hipravin.api.model.*;
import com.hipravin.engine.GraphBuildService;
import com.hipravin.engine.GraphNotFoundException;
import com.hipravin.engine.GraphSimulationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/graph")
public class GraphController {
    private final Logger logger = LoggerFactory.getLogger(GraphController.class);

    private final GraphBuildService graphBuildService;
    private final GraphSimulationService graphSimulationService;

    public GraphController(GraphBuildService graphBuildService, GraphSimulationService graphSimulationService) {
        this.graphBuildService = graphBuildService;
        this.graphSimulationService = graphSimulationService;
    }

    @GetMapping("/sample")
    ResponseEntity<GraphDto> graphSample() {
        return ResponseEntity.ok(graphBuildService.sampleGraph());

    }

    @GetMapping("/{graphid}/simulation/{tick}")
    ResponseEntity<GraphAnimationDto> graphAnimation(@PathVariable("graphid") String graphid, @PathVariable("tick") long tick) {
        return ResponseEntity.ok(graphSimulationService.computeGraphSimulationTick(graphid, tick));
    }

    @ExceptionHandler
    ResponseEntity<?> handleGraphNotFound(GraphNotFoundException e) {
        logger.warn(e.getMessage(), e);

        return new ResponseEntity<Object>("graph not found", new HttpHeaders(), HttpStatus.NOT_FOUND);
//        return ResponseEntity.notFound().build();
    }
}
