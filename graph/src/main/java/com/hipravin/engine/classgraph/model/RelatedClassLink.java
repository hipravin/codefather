package com.hipravin.engine.classgraph.model;

public class RelatedClassLink {
    private final ClassGraphNode to;

    /**
     * how many times this class appears in method arguments, return values, fields, etc.
     */
    private long relationsCount;

    public RelatedClassLink(ClassGraphNode to, long relationsCount) {
        this.to = to;
        this.relationsCount = relationsCount;
    }
}
