package com.infinite.eoa.bson.filter.geojson;

import com.mongodb.client.model.geojson.Geometry;
import com.mongodb.client.model.geojson.MultiLineString;
import com.mongodb.client.model.geojson.NamedCoordinateReferenceSystem;
import com.mongodb.client.model.geojson.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeoMultiLineString extends Geo {

    private final List<List<GeoPosition>> coordinates;

    public GeoMultiLineString(final List<List<GeoPosition>> coordinates) {
        this(null, coordinates);
    }

    public GeoMultiLineString(final GeoNamedCoordinateReferenceSystem coordinateReferenceSystem, final List<List<GeoPosition>> coordinates) {
        super(coordinateReferenceSystem);

        this.coordinates = Collections.unmodifiableList(coordinates);
    }

    @Override
    public GeoJsonType getType() {
        return GeoJsonType.MULTI_LINE_STRING;
    }

    public List<List<GeoPosition>> getCoordinates() {
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

        GeoMultiLineString polygon = (GeoMultiLineString) o;

        if (!coordinates.equals(polygon.coordinates)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + coordinates.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GeoMultiLineString{"
                + "coordinates=" + coordinates
                + ((getCoordinateReferenceSystem() == null) ? "" : ", coordinateReferenceSystem=" + getCoordinateReferenceSystem())
                + '}';
    }

    @Override
    public Geometry getGeometry() {
        ArrayList positionCoordinates = new ArrayList<ArrayList<Position>>();

        for (List<GeoPosition> geoPositionList : coordinates) {
            ArrayList<Position> positions = new ArrayList<Position>();
            for (GeoPosition geoPosition : geoPositionList) {
                positions.add(geoPosition.getGeometry());
            }
            positionCoordinates.add(positions);
        }

        return new MultiLineString(
                new NamedCoordinateReferenceSystem(getCoordinateReferenceSystem().getName()),
                positionCoordinates);
    }
}
