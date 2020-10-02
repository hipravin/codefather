package com.hipravin.engine.classgraph.model;

import java.util.ArrayList;
import java.util.List;

public class ClassGraph {
    private final List<ClassGraphNode> nodes = new ArrayList<>();

    public void addNode(ClassGraphNode node) {
        nodes.add(node);
    }

    public List<ClassGraphNode> getNodes() {
        return nodes;
    }
}
