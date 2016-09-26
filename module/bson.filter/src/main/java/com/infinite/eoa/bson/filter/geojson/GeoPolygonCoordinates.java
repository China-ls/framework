package com.infinite.eoa.bson.filter.geojson;

import com.mongodb.client.model.geojson.PolygonCoordinates;
import com.mongodb.client.model.geojson.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GeoPolygonCoordinates {

    private final List<GeoPosition> exterior;
    private final List<List<GeoPosition>> holes;

    public GeoPolygonCoordinates(final List<GeoPosition> exterior, final List<GeoPosition>... holes) {
        this.exterior = Collections.unmodifiableList(exterior);

        List<List<GeoPosition>> holesList = new ArrayList<List<GeoPosition>>(holes.length);
        for (List<GeoPosition> hole : holes) {
            holesList.add(Collections.unmodifiableList(hole));
        }

        this.holes = Collections.unmodifiableList(holesList);
    }

    public List<GeoPosition> getExterior() {
        return exterior;
    }

    public List<List<GeoPosition>> getHoles() {
        return holes;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        GeoPolygonCoordinates that = (GeoPolygonCoordinates) o;

        if (!exterior.equals(that.exterior)) {
            return false;
        }
        if (!holes.equals(that.holes)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = exterior.hashCode();
        result = 31 * result + holes.hashCode();
        return result;
    }

    @Override
    public String toString() {
        return "GeoPolygonCoordinates{"
                + "exterior=" + exterior
                + (holes.isEmpty() ? "" : ", holes=" + holes)
                + '}';
    }


    public PolygonCoordinates getPolygonCoordinates() {
        ArrayList<Position> exteriorPosition = new ArrayList<Position>();
        ArrayList[] holePositions = new ArrayList[holes.size()];

        for (GeoPosition geoPosition : exterior) {
            exteriorPosition.add(geoPosition.getGeometry());
        }

        for (int i = 0; i < holes.size(); i ++ ) {
            ArrayList<Position> positionItems = new ArrayList<Position>();

            for (GeoPosition geoPosition : holes.get(i)) {
                positionItems.add(geoPosition.getGeometry());
            }
            holePositions[i] = positionItems;
        }

        return new PolygonCoordinates(exteriorPosition, holePositions);
    }
}
