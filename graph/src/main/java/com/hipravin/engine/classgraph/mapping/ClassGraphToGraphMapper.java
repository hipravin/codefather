package com.hipravin.engine.classgraph.mapping;

import com.hipravin.engine.classgraph.model.ClassGraph;
import com.hipravin.engine.model.Graph;

public interface ClassGraphToGraphMapper {
    Graph map(ClassGraph classGraph);
}
