package com.infinite.framework.persistent.obj.geojson;

import com.mongodb.client.model.geojson.Geometry;
import com.mongodb.client.model.geojson.GeometryCollection;
import com.mongodb.client.model.geojson.NamedCoordinateReferenceSystem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class GeoCollection extends Geo {
    private final List<? extends Geo> geometries;

    public GeoCollection(final List<? extends Geo> geometries) {
        this(null, geometries);
    }

    public GeoCollection(final GeoNamedCoordinateReferenceSystem coordinateReferenceSystem,
                         final List<? extends Geo> geometries) {
        super(coordinateReferenceSystem);
        this.geometries = Collections.unmodifiableList(geometries);
    }

    @Override
    public GeoJsonType getType() {
        return GeoJsonType.GEOMETRY_COLLECTION;
    }

    public List<? extends Geo> getGeometries() {
        return geometries;
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

        GeoCollection that = (GeoCollection) o;

        if (!geometries.equals(that.geometries)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + geometries.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GeoCollection{"
                + "geometries=" + geometries
                + ((getCoordinateReferenceSystem() == null) ? "" : ", coordinateReferenceSystem=" + getCoordinateReferenceSystem())
                + '}';
    }

    @Override
    public Geometry getGeometry() {
        ArrayList<Geometry> geometriesList = new ArrayList<Geometry>();
        for (Geo geo : geometries) {
            geometriesList.add(geo.getGeometry());
        }
        return new GeometryCollection(new NamedCoordinateReferenceSystem(getCoordinateReferenceSystem().getName()), geometriesList);
    }
}
