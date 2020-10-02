package com.hipravin.engine.classgraph.build;

import com.hipravin.engine.classgraph.model.ClassGraph;
import com.hipravin.engine.classgraph.model.ClassGraphNode;
import com.hipravin.engine.classgraph.model.ClassNameAndPackage;
import com.hipravin.engine.classgraph.model.ParsedMethodSignature;
import org.apache.bcel.classfile.*;

import java.io.IOException;
import java.util.*;
import java.util.function.Predicate;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BcelClassGraphBuilder {
    private final Collection<Predicate<? super JarEntry>> excludeEntriesPredicates =
            Arrays.asList(nonClassEntries());

    private final Set<String> excludePackagesRegexps;

    public BcelClassGraphBuilder(Set<String> excludePackagesRegexps) {
        this.excludePackagesRegexps = excludePackagesRegexps;
    }

    public ClassGraph build(String jarFileName) throws IOException {

        final List<ParsedJavaClass> parsedJavaClasses = new ArrayList<>();
        final Map<ClassNameAndPackage, ClassGraphNode> projectClasses = new HashMap<>();
        final Map<ClassNameAndPackage, ClassGraphNode> nonProjectClasses = new HashMap<>();

        final ClassGraph result = new ClassGraph();

        try (JarFile jar = new JarFile(jarFileName)) {
            Collections.list(jar.entries()).stream()
                    .filter(e -> excludeEntriesPredicates.stream().noneMatch(p -> p.test(e)))
                    .forEach(e -> {
                        parsedJavaClasses.add(parseJarClassEntry(jarFileName, e));
                    });
        }

        parsedJavaClasses.removeIf(c -> isExcluded(c.getClassNameAndPackage().getClassName()));

        //collect project classes
        parsedJavaClasses.forEach(pjc -> {
            projectClasses.putIfAbsent(pjc.getClassNameAndPackage(),
                    new ClassGraphNode(pjc.getClassNameAndPackage(), true, pjc.getCodeAmountBytes()));
        });

        //collect non project classes
        parsedJavaClasses.forEach(pjc -> {
            pjc.getAllReferences().keySet()
                    .forEach(referredClass -> {
                        if (!projectClasses.containsKey(referredClass)) {
                            nonProjectClasses.putIfAbsent(referredClass,
                                    new ClassGraphNode(referredClass, false, 1));
                        }
                    });
        });
        //set links
        parsedJavaClasses.forEach(pjc -> {
            projectClasses.computeIfPresent(pjc.getClassNameAndPackage(), (c, node) -> {
                pjc.getAllReferences().forEach((referredClass, value) -> {

                    ClassGraphNode to = Optional.ofNullable(projectClasses.get(referredClass))
                            .or(() -> Optional.ofNullable(nonProjectClasses.get(referredClass)))
                            .orElseThrow(() -> new GraphBuildEception("Referred class is neither project nor library"));

                    node.getLinks().merge(to, value, Long::sum);
                });

                return node;
            });
        });

        Stream.concat(projectClasses.values().stream(), nonProjectClasses.values().stream())
                .forEach(result::addNode);

        return result;
    }

    private boolean isExcluded(String className) {
        return excludePackagesRegexps.stream().anyMatch(className::startsWith);
    }

    private ParsedJavaClass parseJarClassEntry(String jarFileName, JarEntry entry) {
        ClassParser parser = new ClassParser(jarFileName, entry.getName());
        try {
            JavaClass javaClass = parser.parse();

            ParsedJavaClass result = new ParsedJavaClass(new ClassNameAndPackage(
                    javaClass.getClassName(), javaClass.getPackageName()));

            for (Field field : javaClass.getFields()) {
                parseField(field)
                        .forEach(result::addFieldReference);
            }

            for (Method method : javaClass.getMethods()) {
                ParsedMethodSignature pms = parseMethod(method);
                Stream.concat(
                        pms.getClassesInMethodParams().stream(),
                        pms.getClassesInReturnType().stream())
                        .forEach(result::addMethodReference);

                Arrays.stream(method.getAttributes())
                        .filter(a -> a instanceof Code)
                        .forEach(a -> result.addCodeAmount(((Code) a).getCode().length));
            }

            return result;

        } catch (IOException e) {
            throw new GraphBuildEception(e);
        }
    }

    private ParsedMethodSignature parseMethod(Method method) {
        return method.getGenericSignature() != null
                ? parseGenericMethodSignature(method.getGenericSignature())
                : parseGenericMethodSignature(method.getSignature());
    }

    private Set<ClassNameAndPackage> parseField(Field field) {
        return Optional.ofNullable(field.getGenericSignature())
                .or(() -> Optional.of(field.getSignature()))
                .map(BcelClassGraphBuilder::findClassesInGenericSignature)
                .orElseThrow(() -> new GraphBuildEception(
                        "Failed to parse field signature: " + field.getSignature() + "/" + field.getGenericSignature()))
                .stream().map(ClassNameAndPackage::fromSlashed)
                .collect(Collectors.toSet());
    }


    private static final Pattern SIGN_PATTERN = Pattern.compile(".*\\((.*)\\)(.*)");
    private static final Pattern SIGN_CLASS_PATERN = Pattern.compile("L([^;<]+)");

    //    public <T, E extends ChessGame> Set<List<ChessGame>> superGeneric(T echessGame, Optional<List<? super Number>> troubleSu, Optional<List<? extends Number>> troubleE, double d, ThreadLocal<AtomicReference<List<King>>> trouble3) {
    //   <T:Ljava/lang/Object;E:Lhipravin/samples/chess/engine/ChessGame;>(TT;Ljava/util/Optional<Ljava/util/List<-Ljava/lang/Number;>;>;Ljava/util/Optional<Ljava/util/List<+Ljava/lang/Number;>;>;DLjava/lang/ThreadLocal<Ljava/util/concurrent/atomic/AtomicReference<Ljava/util/List<Lhipravin/samples/chess/engine/model/piece/King;>;>;>;)Ljava/util/Set<Ljava/util/List<Lhipravin/samples/chess/engine/ChessGame;>;>;
    protected static ParsedMethodSignature parseGenericMethodSignature(String signature) {
        //e.g.  (Ljava/util/List<Lhipravin/samples/chess/api/model/PieceDto;>;)V
        Matcher sm = SIGN_PATTERN.matcher(signature);
        if (sm.find()) {
            String params = sm.group(1);
            String retVal = sm.group(2);

            Set<ClassNameAndPackage> classesInParams = findClassesInGenericSignature(params)
                    .stream()
                    .map(ClassNameAndPackage::fromSlashed)
                    .collect(Collectors.toSet());

            Set<ClassNameAndPackage> classesInReturnType = findClassesInGenericSignature(retVal)
                    .stream()
                    .map(ClassNameAndPackage::fromSlashed)
                    .collect(Collectors.toSet());

            return new ParsedMethodSignature(classesInReturnType, classesInParams);
        }

        throw new GraphBuildEception("Failed to parse method signature: " + signature);
    }

    private static List<String> findClassesInGenericSignature(String signPart) {
        List<String> classes = new ArrayList<>();
        Matcher m = SIGN_CLASS_PATERN.matcher(signPart);

        while (m.find()) {
            classes.add(m.group(1));
        }

        return classes;
    }


    private static Predicate<? super JarEntry> nonClassEntries() {
        return (Predicate<JarEntry>) jarEntry -> !jarEntry.getName().toLowerCase().endsWith(".class");
    }
}
