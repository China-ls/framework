package com.infinite.framework.bson.filter.geojson;

import com.mongodb.client.model.geojson.Geometry;
import com.mongodb.client.model.geojson.NamedCoordinateReferenceSystem;
import com.mongodb.client.model.geojson.Polygon;

import java.util.List;

public class GeoPolygon extends Geo {

    private final GeoPolygonCoordinates coordinates;

    public GeoPolygon(final List<GeoPosition> exterior, final List<GeoPosition>... holes) {
        this(new GeoPolygonCoordinates(exterior, holes));
    }

    public GeoPolygon(final GeoPolygonCoordinates coordinates) {
        this(null, coordinates);
    }

    public GeoPolygon(final GeoNamedCoordinateReferenceSystem coordinateReferenceSystem, final GeoPolygonCoordinates coordinates) {
        super(coordinateReferenceSystem);
        this.coordinates = coordinates;
    }

    @Override
    public GeoJsonType getType() {
        return GeoJsonType.POLYGON;
    }

    public GeoPolygonCoordinates getCoordinates() {
        return coordinates;
    }

    public List<GeoPosition> getExterior() {
        return coordinates.getExterior();
    }

    public List<List<GeoPosition>> getHoles() {
        return coordinates.getHoles();
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

        GeoPolygon polygon = (GeoPolygon) o;

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
        return "GeoPolygon{"
                + "exterior=" + coordinates.getExterior()
                + (coordinates.getHoles().isEmpty() ? "" : ", holes=" + coordinates.getHoles())
                + ((getCoordinateReferenceSystem() == null) ? "" : ", coordinateReferenceSystem=" + getCoordinateReferenceSystem())
                + '}';
    }

    @Override
    public Geometry getGeometry() {
        return new Polygon(
                new NamedCoordinateReferenceSystem(getCoordinateReferenceSystem().getName()),
                coordinates.getPolygonCoordinates());
    }
}
