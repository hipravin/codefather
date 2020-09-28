package com.hipravin.engine.physics;

import com.hipravin.engine.math.Vector2d;

public class Force {
    private Vector2d value;

    public Force(Vector2d value) {
        this.value = value;
    }

    public Vector2d getValue() {
        return value;
    }

    public void setValue(Vector2d value) {
        this.value = value;
    }
}
