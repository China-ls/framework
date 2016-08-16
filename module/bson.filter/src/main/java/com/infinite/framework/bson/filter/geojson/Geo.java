package com.infinite.framework.bson.filter.geojson;


import com.mongodb.client.model.geojson.Geometry;

public abstract class Geo {

    private final GeoNamedCoordinateReferenceSystem coordinateReferenceSystem;

    protected Geo() {
        this(null);
    }

    protected Geo(final GeoNamedCoordinateReferenceSystem coordinateReferenceSystem) {
        this.coordinateReferenceSystem = coordinateReferenceSystem;
    }

    public abstract GeoJsonType getType();

    public abstract Geometry getGeometry();

    public GeoNamedCoordinateReferenceSystem getCoordinateReferenceSystem() {
        return coordinateReferenceSystem;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Geo geometry = (Geo) o;

        if (coordinateReferenceSystem != null ? !coordinateReferenceSystem.equals(geometry.coordinateReferenceSystem)
                : geometry.coordinateReferenceSystem != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return coordinateReferenceSystem != null ? coordinateReferenceSystem.hashCode() : 0;
    }
}