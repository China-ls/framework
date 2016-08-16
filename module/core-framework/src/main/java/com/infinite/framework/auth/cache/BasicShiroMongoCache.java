package com.infinite.framework.auth.cache;

import org.bson.Document;

import java.io.Serializable;

/**
 * Created by hx on 16-7-5.
 */
public class BasicShiroMongoCache extends ShiroMongoCache<Serializable, Serializable> {
    String keyToString(Serializable key) {
        return null;
    }
    Serializable stringToKey(String keyString) {
        return null;
    }
    Serializable documentToValue(Document document) {
        return null;
    }
    Document valueToDocument(Serializable value) {
        return null;
    }
}
