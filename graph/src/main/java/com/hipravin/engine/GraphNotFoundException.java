package com.hipravin.engine;

public class GraphNotFoundException extends RuntimeException {
    public GraphNotFoundException() {
        super();
    }

    public GraphNotFoundException(String message) {
        super(message);
    }

    public GraphNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
