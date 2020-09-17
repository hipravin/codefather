package com.hipravin.api.model;

import java.util.List;

public class GraphDto {
    private List<NodeDto> nodes;

    public GraphDto(List<NodeDto> nodes) {
        this.nodes = nodes;
    }

    public GraphDto() {
    }

    public List<NodeDto> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeDto> nodes) {
        this.nodes = nodes;
    }
}
