//package com.ls.test.service;
//
//import com.mongodb.client.model.TextSearchOptions;
//import com.mongodb.client.model.geojson.Geometry;
//import com.mongodb.client.model.geojson.Point;
//import org.bson.BsonArray;
//import org.bson.BsonBoolean;
//import org.bson.BsonDocument;
//import org.bson.BsonDocumentWriter;
//import org.bson.BsonDouble;
//import org.bson.BsonInt32;
//import org.bson.BsonInt64;
//import org.bson.BsonRegularExpression;
//import org.bson.BsonString;
//import org.bson.BsonType;
//import org.bson.BsonValue;
//import org.bson.codecs.configuration.CodecRegistry;
//
//import java.util.Arrays;
//import java.util.List;
//import java.util.Map;
//import java.util.regex.Pattern;
//
//public class Filters {
//    private Filters() {
//    }
//
//    public static <TItem> IBsonable eq(final String fieldName, final TItem value) {
//        return new SimpleEncodingBsonable<TItem>(fieldName, value);
//    }
//
//    public static <TItem> IBsonable ne(final String fieldName, final TItem value) {
//        return new OperatorBsonable<TItem>("$ne", fieldName, value);
//    }
//
//    public static <TItem> IBsonable gt(final String fieldName, final TItem value) {
//        return new OperatorBsonable<TItem>("$gt", fieldName, value);
//    }
//
//    public static <TItem> IBsonable lt(final String fieldName, final TItem value) {
//        return new OperatorBsonable<TItem>("$lt", fieldName, value);
//    }
//
//    public static <TItem> IBsonable gte(final String fieldName, final TItem value) {
//        return new OperatorBsonable<TItem>("$gte", fieldName, value);
//    }
//
//    public static <TItem> IBsonable lte(final String fieldName, final TItem value) {
//        return new OperatorBsonable<TItem>("$lte", fieldName, value);
//    }
//
//    public static <TItem> IBsonable in(final String fieldName, final TItem... values) {
//        return in(fieldName, Arrays.asList(values));
//    }
//
//    public static <TItem> IBsonable in(final String fieldName, final Iterable<TItem> values) {
//        return new SimpleEncodingBsonable<IBsonable>(fieldName , new IterableOperatorFilter<TItem>("$in", values));
//    }
//
//    public static <TItem> IBsonable nin(final String fieldName, final TItem... values) {
//        return nin(fieldName, Arrays.asList(values));
//    }
//
//    public static <TItem> IBsonable nin(final String fieldName, final Iterable<TItem> values) {
//        return new SimpleEncodingBsonable<IBsonable>(fieldName, new IterableOperatorFilter<TItem>("$nin", values));
//    }
//
//    public static IBsonable and(final Iterable<IBsonable> filters) {
//        return new AndFilter(filters);
//    }
//
//    public static IBsonable and(final IBsonable... filters) {
//        return and(Arrays.asList(filters));
//    }
//
//    public static IBsonable or(final Iterable<IBsonable> filters) {
//        return new OrFilter(filters);
//    }
//
//    public static IBsonable or(final IBsonable... filters) {
//        return or(Arrays.asList(filters));
//    }
//
//    public static IBsonable not(final IBsonable filter) {
//        return new NotFilter(filter);
//    }
//
//    public static IBsonable nor(final IBsonable... filters) {
//        return nor(Arrays.asList(filters));
//    }
//
//    public static IBsonable nor(final Iterable<IBsonable> filters) {
//        return new IterableOperatorFilter<IBsonable>("$nor", filters);
//    }
//
//    public static IBsonable exists(final String fieldName) {
//        return exists(fieldName, true);
//    }
//
//    public static IBsonable exists(final String fieldName, final boolean exists) {
//        return new OperatorBsonable<BsonBoolean>("$exists", fieldName, BsonBoolean.valueOf(exists));
//    }
//
//    public static IBsonable type(final String fieldName, final BsonType type) {
//        return new OperatorBsonable<BsonInt32>("$type", fieldName, new BsonInt32(type.getValue()));
//    }
//
//    public static IBsonable type(final String fieldName, final String type) {
//        return new OperatorBsonable<BsonString>("$type", fieldName, new BsonString(type));
//    }
//
//    public static IBsonable mod(final String fieldName, final long divisor, final long remainder) {
//        return new OperatorBsonable<BsonArray>("$mod", fieldName, new BsonArray(Arrays.asList(new BsonInt64(divisor), new BsonInt64(remainder))));
//    }
//
//    public static IBsonable regex(final String fieldName, final String pattern) {
//        return regex(fieldName, pattern, null);
//    }
//
//    public static IBsonable regex(final String fieldName, final String pattern, final String options) {
//        notNull("pattern", pattern);
//        return new SimpleFilter(fieldName, new BsonRegularExpression(pattern, options));
//    }
//
//    public static IBsonable regex(final String fieldName, final Pattern pattern) {
//        notNull("pattern", pattern);
//        return new SimpleEncodingBsonable<Pattern>(fieldName, pattern);
//    }
//
//    public static IBsonable text(final String search) {
//        notNull("search", search);
//        return text(search, new TextSearchOptions());
//    }
//
//    @Deprecated
//    public static IBsonable text(final String search, final String language) {
//        notNull("search", search);
//        return text(search, new TextSearchOptions().language(language));
//    }
//
//    public static IBsonable text(final String search, final TextSearchOptions textSearchOptions) {
//        notNull("search", search);
//        notNull("textSearchOptions", textSearchOptions);
//        return new IBsonable() {
//            @Override
//            public <TDocument> BsonDocument toBsonDocument(final Class<TDocument> documentClass, final CodecRegistry codecRegistry) {
//                BsonDocument searchDocument = new BsonDocument("$search", new BsonString(search));
//                if (textSearchOptions.getLanguage() != null) {
//                    searchDocument.put("$language", new BsonString(textSearchOptions.getLanguage()));
//                }
//                if (textSearchOptions.getCaseSensitive() != null) {
//                    searchDocument.put("$caseSensitive", BsonBoolean.valueOf(textSearchOptions.getCaseSensitive()));
//                }
//                if (textSearchOptions.getDiacriticSensitive() != null) {
//                    searchDocument.put("$diacriticSensitive", BsonBoolean.valueOf(textSearchOptions.getDiacriticSensitive()));
//                }
//                return new BsonDocument("$text", searchDocument);
//            }
//        };
//    }
//
//    public static IBsonable where(final String javaScriptExpression) {
//        notNull("javaScriptExpression", javaScriptExpression);
//        return new BsonDocument("$where", new BsonString(javaScriptExpression));
//    }
//
//    public static <TItem> IBsonable all(final String fieldName, final TItem... values) {
//        return all(fieldName, Arrays.asList(values));
//    }
//
//    public static <TItem> IBsonable all(final String fieldName, final Iterable<TItem> values) {
//        return new SimpleEncodingBsonable<IBsonable>(fieldName, new IterableOperatorFilter<TItem>("$all", values));
//    }
//
//    public static IBsonable elemMatch(final String fieldName, final IBsonable filter) {
//        return new IBsonable() {
//            @Override
//            public <TDocument> BsonDocument toBsonDocument(final Class<TDocument> documentClass, final CodecRegistry codecRegistry) {
//                return new BsonDocument(fieldName, new BsonDocument("$elemMatch", filter.toBsonDocument(documentClass, codecRegistry)));
//            }
//        };
//    }
//
//    public static IBsonable size(final String fieldName, final int size) {
//        return new OperatorBsonable<Integer>("$size", fieldName, size);
//    }
//
//    public static IBsonable bitsAllClear(final String fieldName, final long bitmask) {
//        return new OperatorBsonable<Long>("$bitsAllClear", fieldName, bitmask);
//    }
//
//    public static IBsonable bitsAllSet(final String fieldName, final long bitmask) {
//        return new OperatorBsonable<Long>("$bitsAllSet", fieldName, bitmask);
//    }
//
//    public static IBsonable bitsAnyClear(final String fieldName, final long bitmask) {
//        return new OperatorBsonable<Long>("$bitsAnyClear", fieldName, bitmask);
//    }
//
//    public static IBsonable bitsAnySet(final String fieldName, final long bitmask) {
//        return new OperatorBsonable<Long>("$bitsAnySet", fieldName, bitmask);
//    }
//
//    public static IBsonable geoWithin(final String fieldName, final Geometry geometry) {
//        return new GeometryOperatorFilter<Geometry>("$geoWithin", fieldName, geometry);
//    }
//
//    public static IBsonable geoWithin(final String fieldName, final IBsonable geometry) {
//        return new GeometryOperatorFilter<IBsonable>("$geoWithin", fieldName, geometry);
//    }
//
//    public static IBsonable geoWithinBox(final String fieldName, final double lowerLeftX, final double lowerLeftY, final double upperRightX,
//                                    final double upperRightY) {
//        BsonDocument box = new BsonDocument("$box",
//                new BsonArray(Arrays.asList(new BsonArray(Arrays.asList(new BsonDouble(lowerLeftX),
//                        new BsonDouble(lowerLeftY))),
//                        new BsonArray(Arrays.asList(new BsonDouble(upperRightX),
//                                new BsonDouble(upperRightY))))));
//        return new OperatorBsonable<BsonDocument>("$geoWithin", fieldName, box);
//    }
//
//    public static IBsonable geoWithinPolygon(final String fieldName, final List<List<Double>> points) {
//        BsonArray pointsArray = new BsonArray();
//        for (List<Double> point : points) {
//            pointsArray.add(new BsonArray(Arrays.asList(new BsonDouble(point.get(0)), new BsonDouble(point.get(1)))));
//        }
//        BsonDocument polygon = new BsonDocument("$polygon", pointsArray);
//        return new OperatorBsonable<BsonDocument>("$geoWithin", fieldName, polygon);
//    }
//
//    public static IBsonable geoWithinCenter(final String fieldName, final double x, final double y, final double radius) {
//        BsonDocument center = new BsonDocument("$center",
//                new BsonArray(Arrays.<BsonValue>asList(new BsonArray(Arrays.asList(new BsonDouble(x),
//                        new BsonDouble(y))),
//                        new BsonDouble(radius))));
//        return new OperatorBsonable<BsonDocument>("$geoWithin", fieldName, center);
//    }
//
//    public static IBsonable geoWithinCenterSphere(final String fieldName, final double x, final double y, final double radius) {
//        BsonDocument centerSphere = new BsonDocument("$centerSphere",
//                new BsonArray(Arrays.<BsonValue>asList(new BsonArray(Arrays.asList(new BsonDouble(x),
//                        new BsonDouble(y))),
//                        new BsonDouble(radius))));
//        return new OperatorBsonable<BsonDocument>("$geoWithin", fieldName, centerSphere);
//    }
//
//    public static IBsonable geoIntersects(final String fieldName, final IBsonable geometry) {
//        return new GeometryOperatorFilter<IBsonable>("$geoIntersects", fieldName, geometry);
//    }
//
//    public static IBsonable geoIntersects(final String fieldName, final Geometry geometry) {
//        return new GeometryOperatorFilter<Geometry>("$geoIntersects", fieldName, geometry);
//    }
//
//    public static IBsonable near(final String fieldName, final Point geometry, final Double maxDistance, final Double minDistance) {
//        return new GeometryOperatorFilter<Point>("$near", fieldName, geometry, maxDistance, minDistance);
//    }
//
//    public static IBsonable near(final String fieldName, final IBsonable geometry, final Double maxDistance, final Double minDistance) {
//        return new GeometryOperatorFilter<IBsonable>("$near", fieldName, geometry, maxDistance, minDistance);
//    }
//
//    public static IBsonable near(final String fieldName, final double x, final double y, final Double maxDistance, final Double minDistance) {
//        return createNearFilterDocument(fieldName, x, y, maxDistance, minDistance, "$near");
//    }
//
//    public static IBsonable nearSphere(final String fieldName, final Point geometry, final Double maxDistance, final Double minDistance) {
//        return new GeometryOperatorFilter<Point>("$nearSphere", fieldName, geometry, maxDistance, minDistance);
//    }
//
//    public static IBsonable nearSphere(final String fieldName, final IBsonable geometry, final Double maxDistance, final Double minDistance) {
//        return new GeometryOperatorFilter<IBsonable>("$nearSphere", fieldName, geometry, maxDistance, minDistance);
//    }
//
//    public static IBsonable nearSphere(final String fieldName, final double x, final double y, final Double maxDistance,
//                                  final Double minDistance) {
//        return createNearFilterDocument(fieldName, x, y, maxDistance, minDistance, "$nearSphere");
//    }
//
//    private static IBsonable createNearFilterDocument(final String fieldName, final double x, final double y, final Double maxDistance,
//                                                 final Double minDistance, final String operator) {
//        BsonDocument nearFilter = new BsonDocument(operator, new BsonArray(Arrays.asList(new BsonDouble(x), new BsonDouble(y))));
//        if (maxDistance != null) {
//            nearFilter.append("$maxDistance", new BsonDouble(maxDistance));
//        }
//        if (minDistance != null) {
//            nearFilter.append("$minDistance", new BsonDouble(minDistance));
//        }
//        return new BsonDocument(fieldName, nearFilter);
//    }
//
//
//    private static final class SimpleFilter implements IBsonable {
//        private final String fieldName;
//        private final BsonValue value;
//
//        private SimpleFilter(final String fieldName, final BsonValue value) {
//            this.fieldName = notNull("fieldName", fieldName);
//            this.value = notNull("value", value);
//        }
//
//        @Override
//        public <TDocument> BsonDocument toBsonDocument(final Class<TDocument> documentClass, final CodecRegistry codecRegistry) {
//            return new BsonDocument(fieldName, value);
//        }
//    }
//
//    private static class AndFilter implements IBsonable {
//        private final Iterable<IBsonable> filters;
//
//        public AndFilter(final Iterable<IBsonable> filters) {
//            this.filters = notNull("filters", filters);
//        }
//
//        @Override
//        public <TDocument> BsonDocument toBsonDocument(final Class<TDocument> documentClass, final CodecRegistry codecRegistry) {
//            BsonDocument andRenderable = new BsonDocument();
//
//            for (IBsonable filter : filters) {
//                BsonDocument renderedRenderable = filter.toBsonDocument(documentClass, codecRegistry);
//                for (Map.Entry<String, BsonValue> element : renderedRenderable.entrySet()) {
//                    addClause(andRenderable, element);
//                }
//            }
//
//            if (andRenderable.isEmpty()) {
//                andRenderable.append("$and", new BsonArray());
//            }
//
//            return andRenderable;
//        }
//
//        private void addClause(final BsonDocument document, final Map.Entry<String, BsonValue> clause) {
//            if (clause.getKey().equals("$and")) {
//                for (BsonValue value : clause.getValue().asArray()) {
//                    for (Map.Entry<String, BsonValue> element : value.asDocument().entrySet()) {
//                        addClause(document, element);
//                    }
//                }
//            } else if (document.size() == 1 && document.keySet().iterator().next().equals("$and")) {
//                document.get("$and").asArray().add(new BsonDocument(clause.getKey(), clause.getValue()));
//            } else if (document.containsKey(clause.getKey())) {
//                if (document.get(clause.getKey()).isDocument() && clause.getValue().isDocument()) {
//                    BsonDocument existingClauseValue = document.get(clause.getKey()).asDocument();
//                    BsonDocument clauseValue = clause.getValue().asDocument();
//                    if (keysIntersect(clauseValue, existingClauseValue)) {
//                        promoteRenderableToDollarForm(document, clause);
//                    } else {
//                        existingClauseValue.putAll(clauseValue);
//                    }
//                } else {
//                    promoteRenderableToDollarForm(document, clause);
//                }
//            } else {
//                document.append(clause.getKey(), clause.getValue());
//            }
//        }
//
//        private boolean keysIntersect(final BsonDocument first, final BsonDocument second) {
//            for (String name : first.keySet()) {
//                if (second.containsKey(name)) {
//                    return true;
//                }
//            }
//            return false;
//        }
//
//        private void promoteRenderableToDollarForm(final BsonDocument document, final Map.Entry<String, BsonValue> clause) {
//            BsonArray clauses = new BsonArray();
//            for (Map.Entry<String, BsonValue> queryElement : document.entrySet()) {
//                clauses.add(new BsonDocument(queryElement.getKey(), queryElement.getValue()));
//            }
//            clauses.add(new BsonDocument(clause.getKey(), clause.getValue()));
//            document.clear();
//            document.put("$and", clauses);
//        }
//    }
//
//    private static class OrFilter implements IBsonable {
//        private final Iterable<IBsonable> filters;
//
//        public OrFilter(final Iterable<IBsonable> filters) {
//            this.filters = notNull("filters", filters);
//        }
//
//        @Override
//        public <TDocument> BsonDocument toBsonDocument(final Class<TDocument> documentClass, final CodecRegistry codecRegistry) {
//            BsonDocument orRenderable = new BsonDocument();
//
//            BsonArray filtersArray = new BsonArray();
//            for (IBsonable filter : filters) {
//                filtersArray.add(filter.toBsonDocument(documentClass, codecRegistry));
//            }
//
//            orRenderable.put("$or", filtersArray);
//
//            return orRenderable;
//        }
//    }
//
//    private static class IterableOperatorFilter<TItem> implements IBsonable {
//        private final String operatorName;
//        private final Iterable<TItem> values;
//
//        IterableOperatorFilter(final String operatorName, final Iterable<TItem> values) {
//            this.operatorName = notNull("operatorName", operatorName);
//            this.values = notNull("values", values);
//        }
//
//        @Override
//        public <TDocument> BsonDocument toBsonDocument(final Class<TDocument> documentClass, final CodecRegistry codecRegistry) {
//            BsonDocumentWriter writer = new BsonDocumentWriter(new BsonDocument());
//            writer.writeStartDocument();
//            writer.writeName(operatorName);
//            writer.writeStartArray();
//            for (TItem value : values) {
//                encodeValue(writer, value, codecRegistry);
//            }
//            writer.writeEndArray();
//            writer.writeEndDocument();
//
//            return writer.getDocument();
//        }
//    }
//
//    private static class NotFilter implements IBsonable {
//        private final IBsonable filter;
//
//        public NotFilter(final IBsonable filter) {
//            this.filter = notNull("filter", filter);
//        }
//
//        @Override
//        public <TDocument> BsonDocument toBsonDocument(final Class<TDocument> documentClass, final CodecRegistry codecRegistry) {
//            BsonDocument filterDocument = filter.toBsonDocument(documentClass, codecRegistry);
//            if (filterDocument.size() == 1) {
//                Map.Entry<String, BsonValue> entry = filterDocument.entrySet().iterator().next();
//                return createFilter(entry.getKey(), entry.getValue());
//            } else {
//                BsonArray values = new BsonArray();
//                for (Map.Entry<String, BsonValue> docs : filterDocument.entrySet()) {
//                    values.add(new BsonDocument(docs.getKey(), docs.getValue()));
//                }
//                return createFilter("$and", values);
//            }
//        }
//
//        private BsonDocument createFilter(final String fieldName, final BsonValue value) {
//            if (fieldName.startsWith("$")) {
//                return new BsonDocument("$not", new BsonDocument(fieldName, value));
//            } else if (value.isDocument() || value.isRegularExpression()) {
//                return new BsonDocument(fieldName, new BsonDocument("$not", value));
//            }
//            return new BsonDocument(fieldName, new BsonDocument("$not", new BsonDocument("$eq", value)));
//        }
//
//    }
//
//    private static class GeometryOperatorFilter<TItem> implements IBsonable {
//        private final String operatorName;
//        private final String fieldName;
//        private final TItem geometry;
//        private final Double maxDistance;
//        private final Double minDistance;
//
//        public GeometryOperatorFilter(final String operatorName, final String fieldName, final TItem geometry) {
//            this(operatorName, fieldName, geometry, null, null);
//        }
//
//        public GeometryOperatorFilter(final String operatorName, final String fieldName, final TItem geometry,
//                                      final Double maxDistance, final Double minDistance) {
//            this.operatorName = operatorName;
//            this.fieldName = notNull("fieldName", fieldName);
//            this.geometry = notNull("geometry", geometry);
//            this.maxDistance = maxDistance;
//            this.minDistance = minDistance;
//        }
//
//        @Override
//        public <TDocument> BsonDocument toBsonDocument(final Class<TDocument> documentClass, final CodecRegistry codecRegistry) {
//            BsonDocumentWriter writer = new BsonDocumentWriter(new BsonDocument());
//            writer.writeStartDocument();
//            writer.writeName(fieldName);
//            writer.writeStartDocument();
//            writer.writeName(operatorName);
//            writer.writeStartDocument();
//            writer.writeName("$geometry");
//            encodeValue(writer, geometry, codecRegistry);
//            if (maxDistance != null) {
//                writer.writeDouble("$maxDistance", maxDistance);
//            }
//            if (minDistance != null) {
//                writer.writeDouble("$minDistance", minDistance);
//            }writer.writeEndDocument();
//            writer.writeEndDocument();
//            writer.writeEndDocument();
//
//            return writer.getDocument();
//        }
//    }
//
//    public static <T> T notNull(final String name, final T value) {
//        if (value == null) {
//            throw new IllegalArgumentException(name + " can not be null");
//        }
//        return value;
//    }
//}
