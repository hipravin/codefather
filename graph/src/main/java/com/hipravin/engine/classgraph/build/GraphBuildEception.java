package com.hipravin.engine.classgraph.build;

public class GraphBuildEception extends RuntimeException {

    public GraphBuildEception() {
        super();
    }

    public GraphBuildEception(String message) {
        super(message);
    }

    public GraphBuildEception(String message, Throwable cause) {
        super(message, cause);
    }

    public GraphBuildEception(Throwable cause) {
        super(cause);
    }

    protected GraphBuildEception(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
