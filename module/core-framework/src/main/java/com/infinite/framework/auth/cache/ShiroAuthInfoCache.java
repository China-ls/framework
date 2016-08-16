package com.infinite.framework.auth.cache;

import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.bson.Document;

/**
 * Created by hx on 16-7-5.
 */
public class ShiroAuthInfoCache extends ShiroMongoCache<String, SimpleAuthenticationInfo> {

    String keyToString(String key) {
        return key;
    }

    String stringToKey(String keyString) {
        return keyString;
    }

    SimpleAuthenticationInfo documentToValue(Document document) {
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo();
        return info;
    }

    Document valueToDocument(SimpleAuthenticationInfo value) {
        return null;
    }

}
