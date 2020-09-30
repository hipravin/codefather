package com.hipravin.engine.physics.graph;

public abstract class GraphPhysicParams {
    public static final double ZERO_DOUBLE = 0.00000001;

    public static final double X_MIN = 0;
    public static final double Y_MIN = 0;
    public static final double X_MAX = 1;
    public static final double Y_MAX = 1;

    public static final double X_EDGE_MIN = -0.01;
    public static final double Y_EDGE_MIN = -0.01;
    public static final double X_EDGE_MAX = 1.01;
    public static final double Y_EDGE_MAX = 1.01;

    public static final double X_MIN_INIT = 0.2;
    public static final double Y_MIN_INIT = 0.2;
    public static final double X_MAX_INIT = 0.8;
    public static final double Y_MAX_INIT = 0.8;

    public static final double EDGE_REPULSION_COEFF = 0.01 * 0.3;
    public static final double PAIRWISE_REPULSION_COEFF = 0.01 * 0.1;

    public static final double LINK_LEN = 0.05;//if distance is greater than this then linked particles are forced towards
    public static final double LINK_GUK_COEFF = 500;

    public static final double VISCOSITY_COEFF = 250;

    public static long MICROTICKS_PER_TICK = 10;
    public static long TICK_PER_SECOND = 60;

    public static double MICROTICK_DT = 1.0d / (MICROTICKS_PER_TICK * TICK_PER_SECOND);

    private GraphPhysicParams() {
    }


}
