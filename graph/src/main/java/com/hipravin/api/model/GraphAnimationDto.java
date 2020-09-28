package com.hipravin.api.model;

public class GraphAnimationDto {
    private GraphDto graph;
    private long tick;

    public GraphAnimationDto() {
    }

    public GraphAnimationDto(GraphDto graph, long tick) {
        this.graph = graph;
        this.tick = tick;
    }

    public GraphDto getGraph() {
        return graph;
    }

    public void setGraph(GraphDto graph) {
        this.graph = graph;
    }

    public long getTick() {
        return tick;
    }

    public void setTick(long tick) {
        this.tick = tick;
    }
}
