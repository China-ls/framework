package com.infinite.eoa.bson.filter;

import com.infinite.eoa.bson.filter.geojson.Geo;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.TextSearchOptions;
import com.mongodb.client.model.geojson.Point;
import org.bson.BsonDocument;
import org.bson.BsonType;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import static java.util.Arrays.asList;

public class PersistentObjectFilters {
    private static final String ID = "_id";

    private Bson bson;

    public static <TItem> PersistentObjectFilters eq(final String fieldName, final TItem value) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.eq(fieldName, ID.equals(fieldName) ? new ObjectId(value.toString()) : value);
        return persistentObjectFilters;
    }

    public static <TItem> PersistentObjectFilters ne(final String fieldName, final TItem value) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.ne(fieldName, ID.equals(fieldName) ? new ObjectId(value.toString()) : value);
        return persistentObjectFilters;
    }

    public static <TItem> PersistentObjectFilters gt(final String fieldName, final TItem value) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.gt(fieldName, value);
        return persistentObjectFilters;
    }

    public static <TItem> PersistentObjectFilters lt(final String fieldName, final TItem value) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.lt(fieldName, value);
        return persistentObjectFilters;
    }

    public static <TItem> PersistentObjectFilters gte(final String fieldName, final TItem value) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.gte(fieldName, value);
        return persistentObjectFilters;
    }

    public static <TItem> PersistentObjectFilters lte(final String fieldName, final TItem value) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.lte(fieldName, value);
        return persistentObjectFilters;
    }

    public static <TItem> PersistentObjectFilters in(final String fieldName, final TItem... values) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        if (ID.equals(fieldName)) {
            ArrayList<ObjectId> ids = new ArrayList<ObjectId>();
            for (TItem item : values) {
                if (null == item) {
                    continue;
                }
                ids.add(new ObjectId(item.toString()));
            }
            persistentObjectFilters.bson = Filters.in(fieldName, ids);
        } else {
            persistentObjectFilters.bson = Filters.in(fieldName, values);
        }
        return persistentObjectFilters;
    }

    public static <TItem> PersistentObjectFilters in(final String fieldName, final Iterable<TItem> values) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();

        if (ID.equals(fieldName)) {
            ArrayList<ObjectId> ids = new ArrayList<ObjectId>();
            for (TItem item : values) {
                if (null == item) {
                    continue;
                }
                ids.add(new ObjectId(item.toString()));
            }
            persistentObjectFilters.bson = Filters.in(fieldName, ids);
        } else {
            persistentObjectFilters.bson = Filters.in(fieldName, values);
        }
        return persistentObjectFilters;
    }

    public static <TItem> PersistentObjectFilters nin(final String fieldName, final TItem... values) {
        return nin(fieldName, asList(values));
    }

    public static <TItem> PersistentObjectFilters nin(final String fieldName, final Iterable<TItem> values) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();

        if (ID.equals(fieldName)) {
            ArrayList<ObjectId> ids = new ArrayList<ObjectId>();
            for (TItem item : values) {
                if (null == item) {
                    continue;
                }
                ids.add(new ObjectId(item.toString()));
            }
            persistentObjectFilters.bson = Filters.nin(fieldName, ids);
        } else {
            persistentObjectFilters.bson = Filters.nin(fieldName, values);
        }

        return persistentObjectFilters;
    }

    public PersistentObjectFilters and(final Iterable<PersistentObjectFilters> filters) {
        ArrayList<Bson> bsons = new ArrayList<Bson>();
        bsons.add(bson);
        for (PersistentObjectFilters filter : filters) {
            bsons.add(filter.bson);
        }
        bson = Filters.and(bsons);
        return this;
    }

    public PersistentObjectFilters and(final PersistentObjectFilters... filters) {
        return and(asList(filters));
    }

    public PersistentObjectFilters or(final Iterable<PersistentObjectFilters> filters) {
        ArrayList<Bson> bsons = new ArrayList<Bson>();
        bsons.add(bson);
        for (PersistentObjectFilters filter : filters) {
            bsons.add(filter.bson);
        }
        bson = Filters.or(bsons);
        return this;
    }

    public PersistentObjectFilters or(final PersistentObjectFilters... filters) {
        return or(asList(filters));
    }

    public static PersistentObjectFilters not(final PersistentObjectFilters filter) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.not(filter.bson);
        return persistentObjectFilters;
    }

    public PersistentObjectFilters nor(final PersistentObjectFilters... filters) {
        return nor(asList(filters));
    }

    public PersistentObjectFilters nor(final Iterable<PersistentObjectFilters> filters) {
        ArrayList<Bson> bsons = new ArrayList<Bson>();
        bsons.add(bson);
        for (PersistentObjectFilters filter : filters) {
            bsons.add(filter.bson);
        }
        bson = Filters.nor(bsons);
        return this;
    }

    public static PersistentObjectFilters exists(final String fieldName) {
        return exists(fieldName, true);
    }

    public static PersistentObjectFilters exists(final String fieldName, final boolean exists) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.exists(fieldName, exists);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters type(final String fieldName, final Type type) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.type(fieldName, BsonType.findByValue(type.getValue()));
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters type(final String fieldName, final String type) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.type(fieldName, type);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters mod(final String fieldName, final long divisor, final long remainder) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.mod(fieldName, divisor, remainder);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters regex(final String fieldName, final String pattern) {
        return regex(fieldName, pattern, null);
    }

    public static PersistentObjectFilters regex(final String fieldName, final String pattern, final String options) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.regex(fieldName, pattern, options);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters regex(final String fieldName, final Pattern pattern) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.regex(fieldName, pattern);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters text(final String search) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.text(search);
        return persistentObjectFilters;
    }

    @Deprecated
    public static PersistentObjectFilters text(final String search, final String language) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.text(search, language);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters text(final String search, final SearchTextOptions searchTextOptions) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.text(search,
                new TextSearchOptions().language(searchTextOptions.getLanguage())
                        .caseSensitive(searchTextOptions.getCaseSensitive())
                        .diacriticSensitive(searchTextOptions.getDiacriticSensitive())
        );
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters where(final String javaScriptExpression) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.where(javaScriptExpression);
        return persistentObjectFilters;
    }

    public static <TItem> PersistentObjectFilters all(final String fieldName, final TItem... values) {
        return all(fieldName, asList(values));
    }

    public static <TItem> PersistentObjectFilters all(final String fieldName, final Iterable<TItem> values) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        if (ID.equals(fieldName)) {
            ArrayList<ObjectId> ids = new ArrayList<ObjectId>();
            for (TItem item : values) {
                if (null == item) {
                    continue;
                }
                ids.add(new ObjectId(item.toString()));
            }
            persistentObjectFilters.bson = Filters.all(fieldName, ids);
        } else {
            persistentObjectFilters.bson = Filters.all(fieldName, values);
        }
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters elemMatch(final String fieldName, final PersistentObjectFilters filter) {
        filter.bson = Filters.elemMatch(fieldName, filter.bson);
        return filter;
    }

    public static PersistentObjectFilters size(final String fieldName, final int size) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.size(fieldName, size);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters bitsAllClear(final String fieldName, final long bitmask) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.bitsAllClear(fieldName, bitmask);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters bitsAllSet(final String fieldName, final long bitmask) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.bitsAllSet(fieldName, bitmask);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters bitsAnyClear(final String fieldName, final long bitmask) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.bitsAnyClear(fieldName, bitmask);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters bitsAnySet(final String fieldName, final long bitmask) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.bitsAnySet(fieldName, bitmask);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters geoWithin(final String fieldName, final Geo geometry) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.geoWithin(fieldName, geometry.getGeometry());
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters geoWithin(final String fieldName, final PersistentObjectFilters filter) {
        filter.bson = Filters.eq(fieldName, filter.bson);
        return filter;
    }

    public static PersistentObjectFilters geoWithinBox(final String fieldName, final double lowerLeftX,
                                                       final double lowerLeftY, final double upperRightX,
                                                       final double upperRightY) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.geoWithinBox(fieldName, lowerLeftX, lowerLeftY, upperRightX, upperRightY);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters geoWithinPolygon(final String fieldName, final List<List<Double>> points) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.geoWithinPolygon(fieldName, points);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters geoWithinCenter(final String fieldName, final double x, final double y, final double radius) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.geoWithinCenter(fieldName, x, y, radius);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters geoWithinCenterSphere(final String fieldName, final double x, final double y, final double radius) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.geoWithinCenterSphere(fieldName, x, y, radius);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters geoIntersects(final String fieldName, final PersistentObjectFilters geometry) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.geoIntersects(fieldName, geometry.bson);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters geoIntersects(final String fieldName, final Geo geometry) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.geoIntersects(fieldName, geometry.getGeometry());
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters near(final String fieldName, final Point geometry, final Double maxDistance, final Double minDistance) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.near(fieldName, geometry, maxDistance, minDistance);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters near(final String fieldName, final PersistentObjectFilters geometry,
                                               final Double maxDistance, final Double minDistance) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.near(fieldName, geometry.bson, maxDistance, minDistance);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters near(final String fieldName, final double x, final double y,
                                               final Double maxDistance, final Double minDistance) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.near(fieldName, x, y, maxDistance, minDistance);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters nearSphere(final String fieldName, final Point geometry,
                                                     final Double maxDistance, final Double minDistance) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.nearSphere(fieldName, geometry, maxDistance, minDistance);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters nearSphere(final String fieldName, final PersistentObjectFilters geometry,
                                                     final Double maxDistance, final Double minDistance) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.nearSphere(fieldName, geometry.bson, maxDistance, minDistance);
        return persistentObjectFilters;
    }

    public static PersistentObjectFilters nearSphere(final String fieldName,
                                                     final double x, final double y,
                                                     final Double maxDistance,
                                                     final Double minDistance) {
        PersistentObjectFilters persistentObjectFilters = new PersistentObjectFilters();
        persistentObjectFilters.bson = Filters.nearSphere(fieldName, x, y, maxDistance, minDistance);
        return persistentObjectFilters;
    }

    public String toJson() {
        return bson.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry()).toJson();
    }

    public Bson getBson() {
        return bson;
    }

    public BsonDocument getDocument() {
        return bson.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry());
    }

}
