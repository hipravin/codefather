package com.hipravin.engine.classgraph.model;

import java.util.*;

public class ClassGraphNode {
    private final ClassNameAndPackage nameAndPackage;
    private final boolean isProjectClass;
    private final long codeComplexity;

    /**
     * class -> how many times it is referred
     */
    private final Map<ClassGraphNode, Long> links = new IdentityHashMap<>();

    public ClassGraphNode(ClassNameAndPackage nameAndPackage, boolean isProjectClass, long codeComplexity) {
        this.nameAndPackage = nameAndPackage;
        this.isProjectClass = isProjectClass;
        this.codeComplexity = codeComplexity;
    }

    public ClassNameAndPackage getNameAndPackage() {
        return nameAndPackage;
    }

    public boolean isProjectClass() {
        return isProjectClass;
    }

    public long getCodeComplexity() {
        return codeComplexity;
    }

    public Map<ClassGraphNode, Long> getLinks() {
        return links;
    }
}
