package com.infinite.framework.bson.filter;

import com.mongodb.MongoClient;
import com.mongodb.client.model.PushOptions;
import com.mongodb.client.model.Updates;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

import static java.util.Arrays.asList;

public class PersistentObjectUpdates {

    public static String combineToJson(final Bson... updates) {
        Bson bson = combine(asList(updates));
        return bson.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()).toJson();
    }

    public static String combineToJson(final List<Bson> updates) {
        Bson bson = combine(updates);
        return bson.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()).toJson();
    }

    public static BsonDocument combineToDocument(final Bson... updates) {
        Bson bson = combine(asList(updates));
        return bson.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry());
    }

    public static BsonDocument combineToDocument(final List<Bson> updates) {
        Bson bson = combine(updates);
        return bson.toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry());
    }

    public static Bson combine(final Bson... updates) {
        return combine(asList(updates));
    }

    public static Bson combine(final List<Bson> updates) {
        return Updates.combine(updates);
    }

    public static <TItem> Bson set(final String fieldName, final TItem value) {
        return Updates.set(fieldName, value);
    }

    public static Bson unset(final String fieldName) {
        return Updates.unset(fieldName);
    }

    public static <TItem> Bson setOnInsert(final String fieldName, final TItem value) {
        return Updates.setOnInsert(fieldName, value);
    }

    public static Bson rename(final String fieldName, final String newFieldName) {
        return Updates.rename(fieldName, newFieldName);
    }

    public static Bson inc(final String fieldName, final Number number) {
        return Updates.inc(fieldName, number);
    }

    public static Bson mul(final String fieldName, final Number number) {
        return Updates.mul(fieldName, number);
    }

    public static <TItem> Bson min(final String fieldName, final TItem value) {
        return Updates.min(fieldName, value);
    }

    public static <TItem> Bson max(final String fieldName, final TItem value) {
        return Updates.max(fieldName, value);
    }

    public static Bson currentDate(final String fieldName) {
        return Updates.currentDate(fieldName);
    }

    public static Bson currentTimestamp(final String fieldName) {
        return Updates.currentTimestamp(fieldName);
    }

    public static <TItem> Bson addToSet(final String fieldName, final TItem value) {
        return Updates.addToSet(fieldName, value);
    }

    public static <TItem> Bson addEachToSet(final String fieldName, final List<TItem> values) {
        return Updates.addEachToSet(fieldName, values);
    }

    public static <TItem> Bson push(final String fieldName, final TItem value) {
        return Updates.push(fieldName, value);
    }

    public static <TItem> Bson pushEach(final String fieldName, final List<TItem> values) {
        return Updates.pushEach(fieldName, values);
    }

    public static <TItem> Bson pushEach(final String fieldName, final List<TItem> values, final PushOptions options) {
        return Updates.pushEach(fieldName, values, options);
    }

    public static <TItem> Bson pull(final String fieldName, final TItem value) {
        return Updates.pull(fieldName, value);
    }

    public static Bson pullByFilter(final Bson filter) {
        return Updates.pullByFilter(filter);
    }

    public static <TItem> Bson pullAll(final String fieldName, final List<TItem> values) {
        return Updates.pullAll(fieldName, values);
    }

    public static Bson popFirst(final String fieldName) {
        return Updates.popFirst(fieldName);
    }

    public static Bson popLast(final String fieldName) {
        return Updates.popLast(fieldName);
    }

    public static Bson bitwiseAnd(final String fieldName, final int value) {
        return Updates.bitwiseAnd(fieldName, value);
    }

    public static Bson bitwiseAnd(final String fieldName, final long value) {
        return Updates.bitwiseAnd(fieldName, value);
    }

    public static Bson bitwiseOr(final String fieldName, final int value) {
        return Updates.bitwiseOr(fieldName, value);
    }

    public static Bson bitwiseOr(final String fieldName, final long value) {
        return Updates.bitwiseOr(fieldName, value);
    }

    public static Bson bitwiseXor(final String fieldName, final int value) {
        return Updates.bitwiseXor(fieldName, value);
    }

    public static Bson bitwiseXor(final String fieldName, final long value) {
        return Updates.bitwiseXor(fieldName, value);
    }


    private PersistentObjectUpdates() {
    }

}
