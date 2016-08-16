package com.infinite.framework.auth.cache;

import com.infinite.framework.core.constant.DbConfig;
import com.infinite.framework.core.util.JsonUtil;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by hx on 16-7-5.
 */
public class ShiroGsonMongoCache<V> extends TextKeyShiroMongoCache<V> {
    private static Logger logger = LoggerFactory.getLogger(ShiroGsonMongoCache.class);

    protected String entityTypeName = DbConfig.AuthenticationInfo.ENTITY_TYPE_NAME;

    V getData(Document document) {
        if (null != document) {
            document.remove(idField);
            Object entityType = document.remove(entityTypeName);
            try {
                return (V) JsonUtil.fromJson(document.toJson(), Class.forName(entityType.toString()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    V documentToValue(Document document) {
        if (null != document) {
            document.remove(idField);
            Object entityType = document.remove(entityTypeName);
            try {
                return (V) JsonUtil.fromJson(document.toJson(), Class.forName(entityType.toString()));
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    Document valueToDocument(V value) {
        return Document.parse(JsonUtil.toJson(value))
                .append(entityTypeName, value.getClass().getName());
    }

}
