package com.infinite.eoa.bson.filter.geojson;

import com.mongodb.client.model.geojson.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeoMultiPolygon extends Geo {

    private final List<GeoPolygonCoordinates> coordinates;

    public GeoMultiPolygon(final List<GeoPolygonCoordinates> coordinates) {
        this(null, coordinates);
    }

    public GeoMultiPolygon(final GeoNamedCoordinateReferenceSystem coordinateReferenceSystem, final List<GeoPolygonCoordinates> coordinates) {
        super(coordinateReferenceSystem);
        this.coordinates = Collections.unmodifiableList(coordinates);
    }

    @Override
    public GeoJsonType getType() {
        return GeoJsonType.MULTI_POLYGON;
    }

    /**
     * Gets the coordinates.
     *
     * @return the coordinates
     */
    public List<GeoPolygonCoordinates> getCoordinates() {
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

        GeoMultiPolygon that = (GeoMultiPolygon) o;

        if (!coordinates.equals(that.coordinates)) {
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
        return "GeoMultiPolygon{"
                + "coordinates=" + coordinates
                + ((getCoordinateReferenceSystem() == null) ? "" : ", coordinateReferenceSystem=" + getCoordinateReferenceSystem())
                + '}';
    }

    @Override
    public Geometry getGeometry() {
        ArrayList<PolygonCoordinates> polygonCoordinates = new ArrayList<PolygonCoordinates>();
        for (GeoPolygonCoordinates geoPolygonCoordinates : coordinates) {
            polygonCoordinates.add(geoPolygonCoordinates.getPolygonCoordinates());
        }
        return new MultiPolygon(
                new NamedCoordinateReferenceSystem(getCoordinateReferenceSystem().getName()),
                polygonCoordinates);
    }
}
