package com.hipravin.engine.classgraph.model;

import java.util.Set;

public class ParsedMethodSignature {
    private final Set<ClassNameAndPackage> classesInReturnType;
    private final Set<ClassNameAndPackage> classesInMethodParams;

    public ParsedMethodSignature(Set<ClassNameAndPackage> classesInReturnType, Set<ClassNameAndPackage> methodParams) {
        this.classesInReturnType = classesInReturnType;
        this.classesInMethodParams = methodParams;
    }

    public Set<ClassNameAndPackage> getClassesInReturnType() {
        return classesInReturnType;
    }

    public Set<ClassNameAndPackage> getClassesInMethodParams() {
        return classesInMethodParams;
    }
}
