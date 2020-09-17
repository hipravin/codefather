package com.hipravin.api.model;

public class NodeDto {
    private Long id;
    //location (center), expected value from 0 to 1
    private PositionDto position;
    private String text;

    public NodeDto(Long id, PositionDto position, String text) {
        this.id = id;
        this.position = position;
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PositionDto getPosition() {
        return position;
    }

    public void setPosition(PositionDto position) {
        this.position = position;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
