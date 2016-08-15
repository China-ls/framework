package com.infinite.framework.service.persistent;

import org.bson.Document;

import java.io.Serializable;
import java.util.Arrays;

public class Filters_bak implements Serializable {
    private Document document;

    public Filters_bak() {
        document = new Document();
    }

    public Filters_bak eq(String field, Object value) {
        document.put(field, value);
        return this;
    }

    public Filters_bak neq(String field, Object value) {
        document.put(field, value);
        return this;
    }

    public Filters_bak gt(String field, Object value) {
        document.put(field, filterOperationToDocument(FilterOperation.GREATER_THAN, value));
        return this;
    }

    public Filters_bak gtOrEq(String field, Object value) {
        document.put(field, filterOperationToDocument(FilterOperation.GREATER_THAN_OR_EQUAL, value));
        return this;
    }

    public Filters_bak lt(String field, Object value) {
        document.put(field, filterOperationToDocument(FilterOperation.LESS_THAN, value));
        return this;
    }

    public Filters_bak ltOrEq(String field, Object value) {
        document.put(field, filterOperationToDocument(FilterOperation.LESS_THAN_OR_EQUAL, value));
        return this;
    }

    public Filters_bak exists(String field, Object value) {
        document.put(field, filterOperationToDocument(FilterOperation.EXISTS, value));
        return this;
    }

    public Filters_bak type(String field, Object value) {
        return this;
    }

    public Filters_bak not(String field, Object value) {
        return this;
    }

    public Filters_bak mod(final String fieldName, final long divisor, final long remainder) {
        return this;
    }

    public Filters_bak size(String field, Object value) {
        return this;
    }

    public Filters_bak in(String field, Object... value) {
        return this;
    }

    public Filters_bak nin(String field, Object... value) {
        return this;
    }

    public Filters_bak all(String field, Object... value) {
        return this;
    }

    public Filters_bak where(String field, Object... value) {
        return this;
    }

    public Filters_bak elemMatch(String field, Filters_bak filters) {
        return this;
    }

    private Document filterOperationToDocument(FilterOperation operation, Object value) {
        return new Document(operation.val(), value);
    }

    private Document filterOperationToDocument(FilterOperation operation, Object[] value) {
        return new Document(operation.val(), Arrays.asList(value));
    }

}
