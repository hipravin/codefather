package com.hipravin.api.model;

public class WeightedNodeDto extends NodeDto {
    private double weight;

    public WeightedNodeDto() {
        super();
    }

    public WeightedNodeDto(Long id, PositionDto position, String text, double weight) {
        super(id, position, text);
        this.weight = weight;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
