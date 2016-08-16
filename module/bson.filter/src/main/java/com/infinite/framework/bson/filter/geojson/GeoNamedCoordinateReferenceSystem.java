package com.infinite.framework.bson.filter.geojson;

public class GeoNamedCoordinateReferenceSystem {
    /**
     * The EPSG:4326 Coordinate Reference System.
     */
    public static final GeoNamedCoordinateReferenceSystem EPSG_4326 =
            new GeoNamedCoordinateReferenceSystem("EPSG:4326");

    /**
     * The urn:ogc:def:crs:OGC:1.3:CRS84 Coordinate Reference System
     */
    public static final GeoNamedCoordinateReferenceSystem CRS_84 =
            new GeoNamedCoordinateReferenceSystem("urn:ogc:def:crs:OGC:1.3:CRS84");

    /**
     * A custom MongoDB EPSG:4326 Coordinate Reference System that uses a strict counter-clockwise winding order.
     *
     * @mongodb.driver.manual reference/operator/query/geometry/ Strict Winding
     */
    public static final GeoNamedCoordinateReferenceSystem EPSG_4326_STRICT_WINDING =
            new GeoNamedCoordinateReferenceSystem("urn:x-mongodb:crs:strictwinding:EPSG:4326");

    private final String name;

    /**
     * Construct an instance
     *
     * @param name the name
     */
    public GeoNamedCoordinateReferenceSystem(final String name) {
        this.name = name;
    }

    public GeoCoordinateReferenceSystemType getType() {
        return GeoCoordinateReferenceSystemType.NAME;
    }

    /**
     * Gets the name of this Coordinate Reference System.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GeoNamedCoordinateReferenceSystem that = (GeoNamedCoordinateReferenceSystem) o;

        if (!name.equals(that.name)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "GeoNamedCoordinateReferenceSystem{"
                + "name='" + name + '\''
                + '}';
    }
}
