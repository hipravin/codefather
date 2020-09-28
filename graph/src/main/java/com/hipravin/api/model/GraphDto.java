package com.hipravin.api.model;

import java.util.List;

public class GraphDto {
    private List<NodeDto> nodes;
    private List<LinkDto> links;

    public GraphDto(List<NodeDto> nodes, List<LinkDto> links) {
        this.nodes = nodes;
        this.links = links;
    }

    public GraphDto() {
    }

    public List<NodeDto> getNodes() {
        return nodes;
    }

    public void setNodes(List<NodeDto> nodes) {
        this.nodes = nodes;
    }

    public List<LinkDto> getLinks() {
        return links;
    }

    public void setLinks(List<LinkDto> links) {
        this.links = links;
    }
}
