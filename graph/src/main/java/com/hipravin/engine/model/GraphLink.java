package com.hipravin.engine.model;

public class GraphLink {
    private double weight = 1;
    private GraphNode to;

    public GraphLink(GraphNode to) {
        this.to = to;
    }

    public GraphLink(double weight, GraphNode to) {
        this.weight = weight;
        this.to = to;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public GraphNode getTo() {
        return to;
    }

    public void setTo(GraphNode to) {
        this.to = to;
    }
}
