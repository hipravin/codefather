package com.hipravin.engine;

import com.hipravin.api.model.*;
import com.hipravin.engine.model.Graph;
import com.hipravin.engine.model.GraphLink;
import com.hipravin.engine.model.GraphNode;
import com.hipravin.engine.model.Metadata;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
public class GraphBuildService {
    public Graph buildOrFindGraph(String id) {
        if(id.startsWith("sample-")) {
            return buildSampleGraph(id);
        }

        throw new GraphNotFoundException("Can't find graph definition for: " + id);
    }

    public Graph buildSampleGraph(String id) {
        switch (id) {
            case "sample-1":
                return buildSample1();
            case "sample-r50":
                return buildRandom(50, 0.01);
            case "sample-r100":
                return buildRandom(100, 0.01);
            default:
                throw new GraphNotFoundException("Can't find sample graph definition for: " + id);
        }
    }

    private Graph buildSample1() {
        Graph graph = new Graph();

        GraphNode node1 = new GraphNode(new ArrayList<>());
        GraphNode node2 = new GraphNode(new ArrayList<>());
        GraphNode node3 = new GraphNode(new ArrayList<>());

        graph.getNodes().add(node1);
        graph.getNodes().add(node2);
        graph.getNodes().add(node3);

        node1.getLinks().add(new GraphLink(node2));
        node2.getLinks().add(new GraphLink(node3));
        node3.getLinks().add(new GraphLink(node1));

        graph.getNodesMetadata().put(node1, new Metadata(1, "Node 1"));
        graph.getNodesMetadata().put(node2, new Metadata(2, "Node 2"));
        graph.getNodesMetadata().put(node3, new Metadata(3, "Node 3"));

        return graph;
    }

    private Graph buildRandom(int nodeCount, double linkProbability) {
        Random random = new Random();
        Graph graph = new Graph();
        List<GraphNode> nodes = Stream.generate(() -> new GraphNode(new ArrayList<>()))
                .limit(nodeCount)
                .collect(Collectors.toList());

        nodes.forEach(n -> graph.getNodes().add(n));

        for (int i = 0; i < nodes.size(); i++) {
             GraphNode node = nodes.get(i);
             graph.getNodesMetadata().put(node, new Metadata(i, "Node " + i));
        }

        for (GraphNode node1 : nodes) {
            for (GraphNode node2 : nodes) {
                if(node1 != node2) {
                    if(random.nextDouble() < linkProbability) {
                        node1.getLinks().add(new GraphLink(node2));
                    }
                }
            }
        }

        return graph;
    }


    public GraphAnimationDto graphAnimation(String graphId, long tick) {
        GraphDto sampleGraph = sampleGraph();
        Random r = new Random(0);

        double delta = 0.001;

        for (int i = 0; i < tick; i++) {

            for (NodeDto node : sampleGraph.getNodes()) {
                node.getPosition().setX(node.getPosition().getX() + delta);
                node.getPosition().setY(node.getPosition().getY() + delta);
                if(node.getPosition().getX() > 1) {
                    node.getPosition().setX(0.1);
                }
                if(node.getPosition().getY() > 1) {
                    node.getPosition().setY(0.1);
                }
            }
        }

        return new GraphAnimationDto(sampleGraph, tick);
    }

    public GraphDto sampleGraph() {
        List<NodeDto> nodes = new ArrayList<>();

        nodes.add(new NodeDto(1L, PositionDto.of(0.1, 0.2), "Node 1"));
        nodes.add(new NodeDto(2L, PositionDto.of(0.3, 0.4), "Node 2"));
        nodes.add(new NodeDto(3L, PositionDto.of(0.4, 0.7), "Node 3"));

        List<LinkDto> links = new ArrayList<>();
        links.add(new LinkDto(1L, 2L));
        links.add(new LinkDto(1L, 3L));
        links.add(new LinkDto(2L, 3L));

        GraphDto graphDto = new GraphDto(nodes, links);

        return graphDto;
    }


}
