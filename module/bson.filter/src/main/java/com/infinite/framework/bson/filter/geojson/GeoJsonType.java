package com.infinite.framework.bson.filter.geojson;

public enum GeoJsonType {

    /**
     * A GeometryCollection
     */
    GEOMETRY_COLLECTION("GeometryCollection"),

    /**
     * A GeoLineString
     */
    LINE_STRING("GeoLineString"),

    /**
     * A GeoMultiLineString
     */
    MULTI_LINE_STRING("GeoMultiLineString"),

    /**
     * A MultiPoint
     */
    MULTI_POINT("MultiPoint"),

    /**
     * A GeoMultiPolygon
     */
    MULTI_POLYGON("GeoMultiPolygon"),

    /**
     * A GeoPoint
     */
    POINT("GeoPoint"),

    /**
     * A GeoPolygon
     */
    POLYGON("GeoPolygon");

    public String getTypeName() {
        return typeName;
    }

    private final String typeName;

    GeoJsonType(final String typeName) {
        this.typeName = typeName;
    }

}
