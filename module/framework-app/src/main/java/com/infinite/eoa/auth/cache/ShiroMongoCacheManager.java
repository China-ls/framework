package com.infinite.eoa.auth.cache;

import com.mongodb.MongoClient;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;

/**
 * Created by hx on 16-7-7.
 */
public class ShiroMongoCacheManager implements CacheManager {
    private final Logger log = LoggerFactory.getLogger(ShiroMongoCacheManager.class);
    private HashMap<String, ShiroMongoCache> cacheMap = new HashMap<String, ShiroMongoCache>(0);
    private MongoClient mongo;

    public ShiroMongoCacheManager() {
    }

    public HashMap<String, ShiroMongoCache> getCacheMap() {
        return cacheMap;
    }

    public void setCacheMap(HashMap<String, ShiroMongoCache> cacheMap) {
        this.cacheMap = cacheMap;
    }

    public MongoClient getMongo() {
        return mongo;
    }

    public void setMongo(MongoClient mongo) {
        this.mongo = mongo;
    }

    public final <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if (log.isTraceEnabled()) {
            log.trace("Acquiring ShiroMongoCache instance named [" + name + "]");
        }

        ShiroMongoCache cache = getCacheMap().get(name);
        if (cache == null) {
            if (log.isInfoEnabled()) {
                log.info("Cache with name '{}' does not yet exist.  Creating now.", name);
            }
            cache = new ShiroGsonMongoCache();
            cacheMap.put(name, cache);
            if (log.isInfoEnabled()) {
                log.info("Added ShiroMongoCache named [" + name + "]");
            }
        } else {
            if (log.isInfoEnabled()) {
                log.info("Using existing ShiroMongoCache named [" + name + "]");
            }
        }
        cache.setCollectionName(name);
        if (cache.getMongo() == null) {
            cache.setMongo(getMongo());
        }
        return cache;
    }

}
