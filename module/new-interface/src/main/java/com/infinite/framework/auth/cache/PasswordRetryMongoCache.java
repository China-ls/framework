package com.infinite.framework.auth.cache;

import org.bson.Document;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by hx on 16-7-5.
 */
public class PasswordRetryMongoCache extends TextKeyShiroMongoCache<AtomicInteger> {
    AtomicInteger documentToValue(Document document) {
        return new AtomicInteger(document.getInteger("atomicinteger"));
    }

    Document valueToDocument(AtomicInteger value) {
        return new Document().append("value", value);
    }
}
