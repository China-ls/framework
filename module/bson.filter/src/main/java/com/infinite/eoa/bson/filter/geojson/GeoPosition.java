package com.infinite.eoa.bson.filter.geojson;

import com.mongodb.client.model.geojson.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeoPosition {

    private final List<Double> values;

    public GeoPosition(final List<Double> values) {
        this.values = Collections.unmodifiableList(values);
    }

    public GeoPosition(final double first, final double second, final double... remaining) {
        List<Double> values = new ArrayList<Double>();
        values.add(first);
        values.add(second);
        for (double cur : remaining) {
            values.add(cur);
        }
        this.values = Collections.unmodifiableList(values);
    }

    public List<Double> getValues() {
        return values;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GeoPosition that = (GeoPosition) o;

        if (!values.equals(that.values)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return values.hashCode();
    }

    @Override
    public String toString() {
        return "GeoPosition{"
                + "values=" + values
                + '}';
    }

    public Position getGeometry() {
        return new Position(values);
    }

}
