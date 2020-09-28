package com.hipravin.engine.model;

public class Metadata {
    private final long id;
    private final String text;

    public Metadata(long id, String text) {
        this.id = id;
        this.text = text;
    }

    public long getId() {
        return id;
    }

    public String getText() {
        return text;
    }
}
