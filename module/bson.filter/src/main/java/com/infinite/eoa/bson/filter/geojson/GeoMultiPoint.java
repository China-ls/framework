package com.infinite.eoa.bson.filter.geojson;

import com.mongodb.client.model.geojson.Geometry;
import com.mongodb.client.model.geojson.MultiPoint;
import com.mongodb.client.model.geojson.NamedCoordinateReferenceSystem;
import com.mongodb.client.model.geojson.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeoMultiPoint extends Geo {
    private final List<GeoPosition> coordinates;

    public GeoMultiPoint(final List<GeoPosition> coordinates) {
        this(null, coordinates);
    }

    public GeoMultiPoint(final GeoNamedCoordinateReferenceSystem coordinateReferenceSystem, final List<GeoPosition> coordinates) {
        super(coordinateReferenceSystem);
        this.coordinates = Collections.unmodifiableList(coordinates);
    }

    @Override
    public GeoJsonType getType() {
        return GeoJsonType.MULTI_POINT;
    }

    public List<GeoPosition> getCoordinates() {
        return coordinates;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        if (!super.equals(o)) {
            return false;
        }

        GeoMultiPoint multiPoint = (GeoMultiPoint) o;

        if (!coordinates.equals(multiPoint.coordinates)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return 31 * result + coordinates.hashCode();
    }

    @Override
    public String toString() {
        return "GeoMultiPoint{"
                + "coordinates=" + coordinates
                + ((getCoordinateReferenceSystem() == null) ? "" : ", coordinateReferenceSystem=" + getCoordinateReferenceSystem())
                + '}';
    }

    @Override
    public Geometry getGeometry() {
        ArrayList<Position> positions = new ArrayList<Position>();
        for (GeoPosition geoPosition : coordinates) {
            positions.add(geoPosition.getGeometry());
        }
        return new MultiPoint(
                new NamedCoordinateReferenceSystem(getCoordinateReferenceSystem().getName()),
                positions);
    }
}
