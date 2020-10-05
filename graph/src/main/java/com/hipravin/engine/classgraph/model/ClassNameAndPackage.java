package com.hipravin.engine.classgraph.model;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Collectors;

public class ClassNameAndPackage {
    /**
     * class name with package
     */
    private final String className;
    /**
     * only package
     */
    private final String classPackage;

    public ClassNameAndPackage(String className, String classPackage) {
        this.className = className;
        this.classPackage = classPackage;
    }

    public static ClassNameAndPackage fromSlashed(String slashed) {
        if (!slashed.contains("/")) {
            return new ClassNameAndPackage(slashed, "nopackage");
//            throw new IllegalArgumentException(slashed + " is not slashed class representation");
        }

        String[] vals = slashed.split("/");
        String classPackage = Arrays.stream(vals)
                .limit(vals.length - 1)
                .collect(Collectors.joining("."));
        String className = vals[vals.length - 1];

        return new ClassNameAndPackage(classPackage + "." + className, classPackage);

    }

    public String getClassNameWithoutPackage() {
        return className.replaceAll("^.*\\.(.*\\..*)$", "$1");//leave last package
    }

    public String getClassName() {
        return className;
    }

    public String getClassPackage() {
        return classPackage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClassNameAndPackage that = (ClassNameAndPackage) o;
        return Objects.equals(className, that.className) &&
                Objects.equals(classPackage, that.classPackage);
    }

    @Override
    public int hashCode() {
        return Objects.hash(className, classPackage);
    }

    @Override
    public String toString() {
        return classPackage + "/" + className;
    }
}
