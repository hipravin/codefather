package com.hipravin.engine.math;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VectorMathTest {
    @Test
    void testSumAmbigous() {
        VectorMath.sum(null, null);
    }
}