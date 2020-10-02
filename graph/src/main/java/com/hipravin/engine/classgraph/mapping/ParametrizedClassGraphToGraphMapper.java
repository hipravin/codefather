package com.hipravin.engine.classgraph.mapping;

import com.hipravin.engine.classgraph.model.ClassGraph;
import com.hipravin.engine.classgraph.model.ClassGraphNode;
import com.hipravin.engine.model.Graph;
import com.hipravin.engine.model.GraphLink;
import com.hipravin.engine.model.GraphNode;
import com.hipravin.engine.model.Metadata;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ParametrizedClassGraphToGraphMapper implements ClassGraphToGraphMapper {

    private boolean includeNonProjectClasses = false;
    private int maxClasses = 120;

    @Override
    public Graph map(ClassGraph classGraph) {
        Graph result = new Graph();

        Map<ClassGraphNode, GraphNode> nodeMapping = new IdentityHashMap<>();
        AtomicLong idCounter = new AtomicLong(0L);

        Set<ClassGraphNode> nodesByComplexity = new TreeSet<>(
                Comparator.comparingLong(ClassGraphNode::getCodeComplexity).reversed()
                        .thenComparing(o -> o.getNameAndPackage().getClassName()));

        nodesByComplexity.addAll(classGraph.getNodes());

        Supplier<Stream<ClassGraphNode>> nodesFilteredStreamSupplier = () ->
                nodesByComplexity.stream()
                        .limit(maxClasses)
                        .filter(cgn -> includeNonProjectClasses || cgn.isProjectClass());

        //map nodes
        nodesFilteredStreamSupplier.get()
                .forEach(cgn -> {
                    GraphNode gn = new GraphNode(nodeWeight(cgn), new ArrayList<>());
                    result.getNodes().add(gn);
                    result.getNodesMetadata().put(gn,
                            new Metadata(idCounter.incrementAndGet(), cgn.getNameAndPackage().getClassNameWithoutPackage()));

                    nodeMapping.put(cgn, gn);
                });

        //map links
        nodesFilteredStreamSupplier.get()
                .forEach(cgn -> {
                    GraphNode gn = nodeMapping.get(cgn);
                    if (gn != null) {
                        cgn.getLinks().forEach((link, value) -> {
                            if(includeNonProjectClasses || link.isProjectClass()) {
                                GraphNode gnTo = nodeMapping.get(link);
                                if(gnTo != null) {
                                    gn.getLinks().add(new GraphLink(linkWeight(value), gnTo));
                                }
                            }
                        });
                    }
                });

        return result;
    }

    private double nodeWeight(ClassGraphNode classGraphNode) {
        long complexity = classGraphNode.getCodeComplexity();

        return complexity < 1000
                ? 1.0
                : Math.min(2.0, Math.sqrt(complexity / 1000.0));
    }

    private double linkWeight(Long relCount) {
        return 1.0;//TODO: stub
    }
}
