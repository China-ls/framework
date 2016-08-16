package com.infinite.framework.bson.filter;

import com.infinite.framework.bson.filter.geojson.Geo;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.TextSearchOptions;
import com.mongodb.client.model.geojson.Geometry;
import com.mongodb.client.model.geojson.Point;
import org.bson.BsonDocument;
import org.bson.BsonType;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static com.sun.corba.se.spi.activation.IIOP_CLEAR_TEXT.value;
import static java.util.Arrays.asList;

public class QueryFilters {

    private Bson bson;

    public static <TItem> QueryFilters eq(final String fieldName, final TItem value) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.eq(fieldName, value);
        return queryFilters;
    }

    public static <TItem> QueryFilters ne(final String fieldName, final TItem value) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.ne(fieldName, value);
        return queryFilters;
    }

    public static <TItem> QueryFilters gt(final String fieldName, final TItem value) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.gt(fieldName, value);
        return queryFilters;
    }

    public static <TItem> QueryFilters lt(final String fieldName, final TItem value) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.lt(fieldName, value);
        return queryFilters;
    }

    public static <TItem> QueryFilters gte(final String fieldName, final TItem value) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.gte(fieldName, value);
        return queryFilters;
    }

    public static <TItem> QueryFilters lte(final String fieldName, final TItem value) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.lte(fieldName, value);
        return queryFilters;
    }

    public static <TItem> QueryFilters in(final String fieldName, final TItem... values) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.in(fieldName, values);
        return queryFilters;
    }

    public static <TItem> QueryFilters in(final String fieldName, final Iterable<TItem> values) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.in(fieldName, values);
        return queryFilters;
    }

    public static <TItem> QueryFilters nin(final String fieldName, final TItem... values) {
        return nin(fieldName, asList(values));
    }

    public static <TItem> QueryFilters nin(final String fieldName, final Iterable<TItem> values) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.nin(fieldName, values);
        return queryFilters;
    }

    public QueryFilters and(final Iterable<QueryFilters> filters) {
        ArrayList<Bson> bsons = new ArrayList<Bson>();
        bsons.add(bson);
        for (QueryFilters filter : filters) {
            bsons.add(filter.bson);
        }
        bson = Filters.and(bsons);
        return this;
    }

    public QueryFilters and(final QueryFilters... filters) {
        return and(asList(filters));
    }

    public QueryFilters or(final Iterable<QueryFilters> filters) {
        ArrayList<Bson> bsons = new ArrayList<Bson>();
        bsons.add(bson);
        for (QueryFilters filter : filters) {
            bsons.add(filter.bson);
        }
        bson = Filters.or(bsons);
        return this;
    }

    public QueryFilters or(final QueryFilters... filters) {
        return or(asList(filters));
    }

    public static QueryFilters not(final QueryFilters filter) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.not(filter.bson);
        return queryFilters;
    }

    public QueryFilters nor(final QueryFilters... filters) {
        return nor(asList(filters));
    }

    public QueryFilters nor(final Iterable<QueryFilters> filters) {
        ArrayList<Bson> bsons = new ArrayList<Bson>();
        bsons.add(bson);
        for (QueryFilters filter : filters) {
            bsons.add(filter.bson);
        }
        bson = Filters.nor(bsons);
        return this;
    }

    public static QueryFilters exists(final String fieldName) {
        return exists(fieldName, true);
    }

    public static QueryFilters exists(final String fieldName, final boolean exists) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.exists(fieldName, exists);
        return queryFilters;
    }

    public static QueryFilters type(final String fieldName, final Type type) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.type(fieldName, BsonType.findByValue(type.getValue()));
        return queryFilters;
    }

    public static QueryFilters type(final String fieldName, final String type) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.type(fieldName, type);
        return queryFilters;
    }

    public static QueryFilters mod(final String fieldName, final long divisor, final long remainder) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.mod(fieldName, divisor, remainder);
        return queryFilters;
    }

    public static QueryFilters regex(final String fieldName, final String pattern) {
        return regex(fieldName, pattern, null);
    }

    public static QueryFilters regex(final String fieldName, final String pattern, final String options) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.regex(fieldName, pattern, options);
        return queryFilters;
    }

    public static QueryFilters regex(final String fieldName, final Pattern pattern) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.regex(fieldName, pattern);
        return queryFilters;
    }

    public static QueryFilters text(final String search) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.text(search);
        return queryFilters;
    }

    @Deprecated
    public static QueryFilters text(final String search, final String language) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.text(search, language);
        return queryFilters;
    }

    public static QueryFilters text(final String search, final SearchTextOptions searchTextOptions) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.text(search,
                new TextSearchOptions().language(searchTextOptions.getLanguage())
                        .caseSensitive(searchTextOptions.getCaseSensitive())
                        .diacriticSensitive(searchTextOptions.getDiacriticSensitive())
        );
        return queryFilters;
    }

    public static QueryFilters where(final String javaScriptExpression) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.where(javaScriptExpression);
        return queryFilters;
    }

    public static <TItem> QueryFilters all(final String fieldName, final TItem... values) {
        return all(fieldName, asList(values));
    }

    public static <TItem> QueryFilters all(final String fieldName, final Iterable<TItem> values) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.all(fieldName, values);
        return queryFilters;
    }

    public static QueryFilters elemMatch(final String fieldName, final QueryFilters filter) {
        filter.bson = Filters.elemMatch(fieldName, filter.bson);
        return filter;
    }

    public static QueryFilters size(final String fieldName, final int size) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.size(fieldName, size);
        return queryFilters;
    }

    public static QueryFilters bitsAllClear(final String fieldName, final long bitmask) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.bitsAllClear(fieldName, bitmask);
        return queryFilters;
    }

    public static QueryFilters bitsAllSet(final String fieldName, final long bitmask) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.bitsAllSet(fieldName, bitmask);
        return queryFilters;
    }

    public static QueryFilters bitsAnyClear(final String fieldName, final long bitmask) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.bitsAnyClear(fieldName, bitmask);
        return queryFilters;
    }

    public static QueryFilters bitsAnySet(final String fieldName, final long bitmask) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.bitsAnySet(fieldName, bitmask);
        return queryFilters;
    }

    public static QueryFilters geoWithin(final String fieldName, final Geo geometry) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.geoWithin(fieldName, geometry.getGeometry());
        return queryFilters;
    }

    public static QueryFilters geoWithin(final String fieldName, final QueryFilters filter) {
        filter.bson = Filters.eq(fieldName, filter.bson);
        return filter;
    }

    public static QueryFilters geoWithinBox(final String fieldName, final double lowerLeftX, final double lowerLeftY, final double upperRightX,
                                            final double upperRightY) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.eq(fieldName, value);
        return queryFilters;
    }

    public static QueryFilters geoWithinPolygon(final String fieldName, final List<List<Double>> points) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.eq(fieldName, value);
        return queryFilters;
    }

    public static QueryFilters geoWithinCenter(final String fieldName, final double x, final double y, final double radius) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.eq(fieldName, value);
        return queryFilters;
    }

    public static QueryFilters geoWithinCenterSphere(final String fieldName, final double x, final double y, final double radius) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.eq(fieldName, value);
        return queryFilters;
    }

    public static QueryFilters geoIntersects(final String fieldName, final QueryFilters geometry) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.eq(fieldName, value);
        return queryFilters;
    }

    public static QueryFilters geoIntersects(final String fieldName, final Geometry geometry) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.eq(fieldName, value);
        return queryFilters;
    }

    public static QueryFilters near(final String fieldName, final Point geometry, final Double maxDistance, final Double minDistance) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.eq(fieldName, value);
        return queryFilters;
    }

    public static QueryFilters near(final String fieldName, final QueryFilters geometry, final Double maxDistance, final Double minDistance) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.eq(fieldName, value);
        return queryFilters;
    }

    public static QueryFilters near(final String fieldName, final double x, final double y, final Double maxDistance, final Double minDistance) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.eq(fieldName, value);
        return queryFilters;
    }

    public static QueryFilters nearSphere(final String fieldName, final Point geometry, final Double maxDistance, final Double minDistance) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.eq(fieldName, value);
        return queryFilters;
    }

    public static QueryFilters nearSphere(final String fieldName, final QueryFilters geometry, final Double maxDistance, final Double minDistance) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.eq(fieldName, value);
        return queryFilters;
    }

    public static QueryFilters nearSphere(final String fieldName, final double x, final double y, final Double maxDistance,
                                          final Double minDistance) {
        QueryFilters queryFilters = new QueryFilters();
        queryFilters.bson = Filters.eq(fieldName, value);
        return queryFilters;
    }

    public String toJson() {
        return ((BsonDocument) bson).toJson();
    }


    public static void main(String[] args) {
    }

}
