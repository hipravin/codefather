package com.hipravin.engine.classgraph.build;

import com.hipravin.engine.classgraph.model.ClassNameAndPackage;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParsedJavaClass {
    private final ClassNameAndPackage classNameAndPackage;

    //Class to count
    private final Map<ClassNameAndPackage, Long> methodReferences = new HashMap<>();
    private final Map<ClassNameAndPackage, Long> fieldReferences = new HashMap<>();
    private long codeAmountBytes = 0;

    public ParsedJavaClass(ClassNameAndPackage classNameAndPackage) {
        this.classNameAndPackage = classNameAndPackage;
    }

    public void addMethodReference(ClassNameAndPackage usedClass) {
        methodReferences.putIfAbsent(usedClass, 0L);
        methodReferences.computeIfPresent(usedClass, (k, v) -> v + 1);
    }

    public void addFieldReference(ClassNameAndPackage usedClass) {
        fieldReferences.putIfAbsent(usedClass, 0L);
        fieldReferences.computeIfPresent(usedClass, (k, v) -> v + 1);
    }

    public void addCodeAmount(long codeInBytes) {
        this.codeAmountBytes += codeInBytes;
    }

    public ClassNameAndPackage getClassNameAndPackage() {
        return classNameAndPackage;
    }

    public Map<ClassNameAndPackage, Long> getMethodReferences() {
        return methodReferences;
    }

    public Map<ClassNameAndPackage, Long> getFieldReferences() {
        return fieldReferences;
    }

    public Map<ClassNameAndPackage, Long> getAllReferences() {
        return Stream.concat(
                methodReferences.entrySet().stream(),
                fieldReferences.entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Long::sum));
    }

    public long getCodeAmountBytes() {
        return codeAmountBytes;
    }
}
