package com.ls.test.service;

/**
 * Created by hx on 16-8-11.
 */
public class SimpleEncodingBsonable<TItem> extends AbstractBsonable {
    private final String fieldName;
    private final TItem value;

    public SimpleEncodingBsonable(final String fieldName, final TItem value) {
        this.fieldName = notNull("fieldName", fieldName);
        this.value = value;
    }
}
