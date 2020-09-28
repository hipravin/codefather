package com.hipravin.engine.model;

import java.util.*;

public class Graph {
    private List<GraphNode> nodes = new ArrayList<>();
    private Map<GraphNode, Metadata> nodesMetadata = new IdentityHashMap<>();

    public Graph(List<GraphNode> nodes) {
        this.nodes = nodes;
    }

    public Graph() {
    }

    public List<GraphNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<GraphNode> nodes) {
        this.nodes = nodes;
    }

    public Map<GraphNode, Metadata> getNodesMetadata() {
        return nodesMetadata;
    }
}
