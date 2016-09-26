package com.infinite.eoa.bson.filter.geojson;

/**
 * Created by hx on 16-8-11.
 */
public enum GeoCoordinateReferenceSystemType {
    /**
     * A coordinate reference system that is specifed by name
     */
    NAME("name"),

    /**
     * A coordinate reference system that is specifed by a dereferenceable URI
     */
    LINK("link");

    /**
     * Gets the GeoJSON-defined name for the type.
     *
     * @return the GeoJSON-defined type name
     */
    public String getTypeName() {
        return typeName;
    }

    private final String typeName;

    GeoCoordinateReferenceSystemType(final String typeName) {
        this.typeName = typeName;
    }
}
