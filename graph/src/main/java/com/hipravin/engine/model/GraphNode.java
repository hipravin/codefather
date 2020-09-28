package com.hipravin.engine.model;

import java.util.ArrayList;
import java.util.List;

public class GraphNode {
    /**
     * Some relative value of node (e.g. code complexity for class graph)
      */
    private double weight = 1;
    /**
     * Weighted links
     */
    private List<GraphLink> links = new ArrayList<>();

    public GraphNode(List<GraphLink> links) {
        this.links = links;
    }

    public GraphNode(double value, List<GraphLink> links) {
        this.weight = value;
        this.links = links;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public List<GraphLink> getLinks() {
        return links;
    }

    public void setLinks(List<GraphLink> links) {
        this.links = links;
    }
}
