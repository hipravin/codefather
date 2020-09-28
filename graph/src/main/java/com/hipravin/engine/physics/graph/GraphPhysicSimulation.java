package com.hipravin.engine.physics.graph;

import com.hipravin.api.model.*;
import com.hipravin.engine.model.Graph;
import com.hipravin.engine.model.GraphLink;
import com.hipravin.engine.model.GraphNode;
import com.hipravin.engine.model.Metadata;

import java.util.ArrayList;
import java.util.List;

public class GraphPhysicSimulation {
    private final Graph graph;
    private final GraphParticleSystem graphParticleSystem;

    private long currentTick = 0;

    public GraphPhysicSimulation(Graph graph, GraphParticleSystem graphParticleSystem) {
        this.graph = graph;
        this.graphParticleSystem = graphParticleSystem;
    }

    public GraphAnimationDto mapCurrentStateToAnimationDto() {
        List<NodeDto> nodes = new ArrayList<>();
        List<LinkDto> links = new ArrayList<>();

        for (GraphNode graphNode : graph.getNodes()) {
            Metadata nodeMetadata = graph.getNodesMetadata().get(graphNode);
            NodeDto nodeDto = new NodeDto(nodeMetadata.getId(),
                    DtoMappingUtil.toPositionDto(graphParticleSystem.getGraphNodeLocation(graphNode)),
                    nodeMetadata.getText());

            nodes.add(nodeDto);

            //links
            for (GraphLink link : graphNode.getLinks()) {
                Metadata nodeToMetadata = graph.getNodesMetadata().get(link.getTo());

                links.add(new LinkDto(nodeMetadata.getId(), nodeToMetadata.getId()));
            }
        }

        GraphDto graphDto = new GraphDto(nodes, links);

        return new GraphAnimationDto(graphDto, currentTick);
    }

    public void nextTick() {
        for (int i = 0; i < GraphPhysicParams.MICROTICKS_PER_TICK; i++) {
             graphParticleSystem.tick(GraphPhysicParams.MICROTICK_DT);
        }
        currentTick++;
    }

    public void advanceToTick(long tick) {
        synchronized (this) {
            while (currentTick < tick) {
                nextTick();
            }
        }
    }

    public long getCurrentTick() {
        return currentTick;
    }
}
