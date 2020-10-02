package com.hipravin.engine.classgraph.mapping;

import com.hipravin.engine.classgraph.model.ClassGraph;
import com.hipravin.engine.classgraph.model.ClassGraphNode;
import com.hipravin.engine.model.Graph;
import com.hipravin.engine.model.GraphLink;
import com.hipravin.engine.model.GraphNode;
import com.hipravin.engine.model.Metadata;

import java.util.ArrayList;
import java.util.IdentityHashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

public class ParametrizedClassGraphToGraphMapper implements ClassGraphToGraphMapper {

    private boolean includeNonProjectClasses = false;


    @Override
    public Graph map(ClassGraph classGraph) {
        Graph result = new Graph();

        Map<ClassGraphNode, GraphNode> nodeMapping = new IdentityHashMap<>();
        AtomicLong idCounter = new AtomicLong(0L);

        //map nodes
        classGraph.getNodes().stream().filter(cgn -> includeNonProjectClasses || cgn.isProjectClass())
                .forEach(cgn -> {
                    GraphNode gn = new GraphNode(nodeWeight(cgn), new ArrayList<>());
                    result.getNodes().add(gn);
                    result.getNodesMetadata().put(gn,
                            new Metadata(idCounter.incrementAndGet(), cgn.getNameAndPackage().getClassNameWithoutPackage()));

                    nodeMapping.put(cgn, gn);
                });


        //map links
        classGraph.getNodes().stream().filter(cgn -> includeNonProjectClasses || cgn.isProjectClass())
                .forEach(cgn -> {
                    GraphNode gn = nodeMapping.get(cgn);
                    if (gn != null) {
                        cgn.getLinks().forEach((link, value) -> {
                            if(includeNonProjectClasses || link.isProjectClass()) {
                                GraphNode gnTo = nodeMapping.get(link);
                                gn.getLinks().add(new GraphLink(linkWeight(value), gnTo));
                            }
                        });
                    }
                });

        return result;
    }

    private double nodeWeight(ClassGraphNode classGraphNode) {
        return 1.0;//TODO: stub
    }

    private double linkWeight(Long relCount) {
        return 1.0;//TODO: stub
    }
}
