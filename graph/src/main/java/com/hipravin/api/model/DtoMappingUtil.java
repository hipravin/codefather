package com.hipravin.api.model;

import com.hipravin.engine.math.Point2d;

public final class DtoMappingUtil {
    private DtoMappingUtil() {
    }

    public static PositionDto toPositionDto(Point2d point2d) {
        return new PositionDto(point2d.getX(), point2d.getY());
    }

}
