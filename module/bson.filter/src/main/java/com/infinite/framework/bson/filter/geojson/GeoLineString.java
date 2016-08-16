package com.infinite.framework.bson.filter.geojson;

import com.mongodb.client.model.geojson.Geometry;
import com.mongodb.client.model.geojson.LineString;
import com.mongodb.client.model.geojson.NamedCoordinateReferenceSystem;
import com.mongodb.client.model.geojson.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class GeoLineString extends Geo {

    private final List<GeoPosition> coordinates;

    public GeoLineString(final List<GeoPosition> coordinates) {
        this(null, coordinates);
    }

    public GeoLineString(final GeoNamedCoordinateReferenceSystem coordinateReferenceSystem,
                         final List<GeoPosition> coordinates) {
        super(coordinateReferenceSystem);
        this.coordinates = Collections.unmodifiableList(coordinates);
    }

    @Override
    public GeoJsonType getType() {
        return GeoJsonType.LINE_STRING;
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

        GeoLineString lineString = (GeoLineString) o;

        if (!coordinates.equals(lineString.coordinates)) {
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
        return "GeoLineString{"
                + "coordinates=" + coordinates
                + ((getCoordinateReferenceSystem() == null) ? "" : ", coordinateReferenceSystem=" + getCoordinateReferenceSystem())
                + '}';
    }

    @Override
    public Geometry getGeometry() {
        ArrayList<Position> coordinatePositions = new ArrayList<Position>();
        for (GeoPosition p : coordinates) {
            coordinatePositions.add(p.getGeometry());
        }
        return new LineString(new NamedCoordinateReferenceSystem(getCoordinateReferenceSystem().getName()), coordinatePositions);
    }
}
