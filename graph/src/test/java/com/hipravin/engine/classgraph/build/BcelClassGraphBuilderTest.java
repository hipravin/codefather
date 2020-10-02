package com.hipravin.engine.classgraph.build;

import com.hipravin.engine.classgraph.model.ClassGraph;
import com.hipravin.engine.classgraph.model.ClassNameAndPackage;
import com.hipravin.engine.classgraph.model.ParsedMethodSignature;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Collections;
import java.util.regex.Pattern;

import static org.junit.jupiter.api.Assertions.*;

class BcelClassGraphBuilderTest {
    String jarName = "src/test/resources/chess-sample.jar";

    @Test
    void testParseGraphChess() throws IOException {
        BcelClassGraphBuilder graphBuilder = new BcelClassGraphBuilder(Collections.singleton("org.springframework.boot"));
        ClassGraph classGraph = graphBuilder.build(jarName);

        System.out.println("Node count " +  classGraph.getNodes().size());

        int toto = classGraph.getNodes().stream()
                .flatMap(n1 -> n1.getLinks().keySet().stream())
                .map(n2 -> n2.getLinks().keySet().size())
                .reduce(Integer::sum).orElse(-1);

        System.out.println("Toto: " + toto);


        System.out.println(classGraph);
    }

    @Test
    void tectCnp() {

        System.out.println("a.b.c.d.E$F".replaceAll("^.*\\.(.*\\..*)$", "$1"));
    }

    @Test
    void testParseGenericSignature() {
        //    public <T, E extends ChessGame> Set<List<ChessGame>> superGeneric(T echessGame, Optional<List<? super Number>> troubleSu, Optional<List<? extends Number>> troubleE, double d, ThreadLocal<AtomicReference<List<King>>> trouble3) {
        String sign = "<T:Ljava/lang/Object;E:Lhipravin/samples/chess/engine/ChessGame;>(TT;Ljava/util/Optional<Ljava/util/List<-Ljava/lang/Number;>;>;Ljava/util/Optional<Ljava/util/List<+Ljava/lang/Number;>;>;DLjava/lang/ThreadLocal<Ljava/util/concurrent/atomic/AtomicReference<Ljava/util/List<Lhipravin/samples/chess/engine/model/piece/King;>;>;>;)Ljava/util/Set<Ljava/util/List<Lhipravin/samples/chess/engine/ChessGame;>;>;";

        ParsedMethodSignature parsedSign = BcelClassGraphBuilder.parseGenericMethodSignature(sign);

        assertEquals(3, parsedSign.getClassesInReturnType().size());
        assertEquals(6, parsedSign.getClassesInMethodParams().size());

        assertTrue(parsedSign.getClassesInReturnType().contains(
                new ClassNameAndPackage("hipravin.samples.chess.engine.ChessGame", "hipravin.samples.chess.engine")));
        assertTrue(parsedSign.getClassesInMethodParams().contains(
                new ClassNameAndPackage("hipravin.samples.chess.engine.model.piece.King", "hipravin.samples.chess.engine.model.piece")));
    }

    @Test
    void testCommonSignature() {
        String sign = "()Lhipravin/samples/chess/api/model/ColorDto;";

        ParsedMethodSignature parsedSign = BcelClassGraphBuilder.parseGenericMethodSignature(sign);

        assertEquals(1, parsedSign.getClassesInReturnType().size());
        assertEquals(0, parsedSign.getClassesInMethodParams().size());

        assertTrue(parsedSign.getClassesInReturnType().contains(
                new ClassNameAndPackage("hipravin.samples.chess.api.model.ColorDto", "hipravin.samples.chess.api.model")));
    }
}