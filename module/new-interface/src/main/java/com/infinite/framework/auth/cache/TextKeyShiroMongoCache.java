package com.infinite.framework.auth.cache;

/**
 * Created by hx on 16-7-5.
 */
public abstract class TextKeyShiroMongoCache<V> extends ShiroMongoCache<String, V> {
    String keyToString(String key) {
        return key;
    }

    String stringToKey(String keyString) {
        return keyString;
    }
}
