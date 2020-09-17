package com.hipravin.api.controller;

import com.hipravin.api.model.GraphDto;
import com.hipravin.api.model.NodeDto;
import com.hipravin.api.model.PositionDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/v1/graph")
public class GraphController {

    @GetMapping("/sample")
    ResponseEntity<GraphDto> graphSample() {

        List<NodeDto> nodes = new ArrayList<>();

        nodes.add(new NodeDto(1L, PositionDto.of(0.1, 0.2), "Node 1"));
        nodes.add(new NodeDto(2L, PositionDto.of(0.3, 0.4), "Node 2"));
        nodes.add(new NodeDto(3L, PositionDto.of(0.4, 0.7), "Node 3"));
        GraphDto graphDto = new GraphDto(nodes);

        return ResponseEntity.ok(graphDto);
    }
}
