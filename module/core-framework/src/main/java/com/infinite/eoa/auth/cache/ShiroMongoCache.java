package com.infinite.eoa.auth.cache;

import com.infinite.eoa.core.constant.DbConfig;
import com.infinite.eoa.core.persistent.MongoDAO;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.model.Filters;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.bson.BsonType;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by hx on 16-7-5.
 */
public abstract class ShiroMongoCache<K, V> extends MongoDAO implements Cache<K, V> {
    private static Logger logger = LoggerFactory.getLogger(ShiroMongoCache.class);

    protected String dbName = DbConfig.AuthenticationInfo.DB;
    protected String collectionName = DbConfig.AuthenticationInfo.COLLECTION;
    protected String idField = DbConfig.AuthenticationInfo.ENTITY_ID_FIELD;

    public V get(K key) throws CacheException {
        Document document = findFirst(dbName, collectionName, Filters.eq(idField, keyToString(key)));
        V value = getData(document);
        logger.debug("get from cached [key:{}, value:{}]", key, value);
        return value;
    }

    public V put(K key, V value) throws CacheException {
        if (!StringUtils.isEmpty(keyToString(key)) && null != value) {
            Document document = valueToDocument(value);
            document.put(idField, key);
            insertOne(dbName, collectionName, document);
            logger.debug("put into cached [key:{}, value:{}]", key, value);
        } else {
            logger.debug("will not put into cached [key:{}, value:{}]", key, value);
        }
        return value;
    }

    public V remove(K key) throws CacheException {
        Document document = findOneAndDelete(dbName, collectionName, Filters.eq(idField, keyToString(key)));
        V value = getData(document);
        logger.debug("remove from cached [key:{}, value:{}]", key, value);
        return value;
    }

    public void clear() throws CacheException {
        drop(dbName, collectionName);
        logger.debug("clear cache {} ", collectionName);
    }

    public int size() {
        int count = (int) count(dbName, collectionName);
        logger.debug("size of cache is :{} ", count);
        return count;
    }

    public Set<K> keys() {
        MongoCursor<Document> documentCursor = find(dbName, collectionName).projection(Filters.type(idField, BsonType.STRING)).iterator();
        Set<K> keys = new HashSet<K>();
        while (documentCursor.hasNext()) {
            keys.add(stringToKey(documentCursor.next().getString(idField)));
        }
        logger.debug("keys : {} ", keys);
        return keys;
    }

    public Collection<V> values() {
        return null;
    }

    V getData(Document document) {
        if (null != document) {
            document.remove(idField);
            return documentToValue(document);
        }
        return null;
    }

    abstract String keyToString(K key);

    abstract K stringToKey(String keyString);

    abstract V documentToValue(Document document);

    abstract Document valueToDocument(V value);

    //****************** getter and setter *******************//

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getCollectionName() {
        return collectionName;
    }

    public void setCollectionName(String collectionName) {
        this.collectionName = collectionName;
    }

    public String getIdField() {
        return idField;
    }

    public void setIdField(String idField) {
        this.idField = idField;
    }

}
