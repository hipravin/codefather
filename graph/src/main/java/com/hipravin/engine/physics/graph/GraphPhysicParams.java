package com.hipravin.engine.physics.graph;

public abstract class GraphPhysicParams {
    public static final double X_MIN = 0;
    public static final double Y_MIN = 0;
    public static final double X_MAX = 1;
    public static final double Y_MAX = 1;

    public static final double X_MIN_INIT = 0.2;
    public static final double Y_MIN_INIT = 0.2;
    public static final double X_MAX_INIT = 0.8;
    public static final double Y_MAX_INIT = 0.8;

    public static final double EDGE_REPULSION_COEFF = 0.1;


    public static long MICROTICKS_PER_TICK = 100;
    public static long TICK_PER_SECOND = 60;

    public static double MICROTICK_DT = 1.0d / (MICROTICKS_PER_TICK * TICK_PER_SECOND);

    private GraphPhysicParams() {
    }


}
