package com.infinite.eoa.bson.filter.geojson;

import com.mongodb.client.model.geojson.Geometry;
import com.mongodb.client.model.geojson.NamedCoordinateReferenceSystem;
import com.mongodb.client.model.geojson.Point;

public class GeoPoint extends Geo {
    private final GeoPosition coordinate;

    /**
     * Construct an instance with the given coordinate.
     *
     * @param coordinate the non-null coordinate of the point
     */
    public GeoPoint(final GeoPosition coordinate) {
        this(null, coordinate);
    }

    /**
     * Construct an instance with the given coordinate and coordinate reference system.
     *
     * @param coordinateReferenceSystem the coordinate reference system
     * @param coordinate                the non-null coordinate of the point
     */
    public GeoPoint(final GeoNamedCoordinateReferenceSystem coordinateReferenceSystem, final GeoPosition coordinate) {
        super(coordinateReferenceSystem);
        this.coordinate = coordinate;
    }

    @Override
    public GeoJsonType getType() {
        return GeoJsonType.POINT;
    }

    public GeoPosition getCoordinates() {
        return coordinate;
    }

    public GeoPosition getPosition() {
        return coordinate;
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

        GeoPoint point = (GeoPoint) o;

        if (!coordinate.equals(point.coordinate)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        return 31 * result + coordinate.hashCode();
    }

    @Override
    public String toString() {
        return "GeoPoint{"
                + "coordinate=" + coordinate
                + ((getCoordinateReferenceSystem() == null) ? "" : ", coordinateReferenceSystem=" + getCoordinateReferenceSystem())
                + '}';
    }

    @Override
    public Geometry getGeometry() {
        return new Point(
                new NamedCoordinateReferenceSystem(getCoordinateReferenceSystem().getName()),
                coordinate.getGeometry());
    }
}
