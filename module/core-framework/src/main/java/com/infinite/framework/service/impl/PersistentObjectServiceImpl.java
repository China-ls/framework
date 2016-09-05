package com.infinite.framework.service.impl;

import com.infinite.framework.bson.filter.bulk.BulkWrite;
import com.infinite.framework.persistent.PersistentObjectDAO;
import com.infinite.framework.service.ApplicationService;
import com.infinite.framework.service.PersistentObjectService;
import com.infinite.framework.service.entity.BulkWirteResultModel;
import com.infinite.framework.service.exception.InvalidDataException;
import com.infinite.framework.service.persistent.obj.BasicEntityConvert;
import com.infinite.framework.service.persistent.obj.IDataConverter;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.FindIterable;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Projections;
import com.mongodb.client.model.Sorts;
import com.mongodb.client.model.WriteModel;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonInvalidOperationException;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by hx on 16-8-9.
 * @since 1.0
 */
@Service("PersistentObjectService")
public class PersistentObjectServiceImpl implements PersistentObjectService {
    private static final Logger log = LoggerFactory.getLogger(PersistentObjectService.class);

    private String appkeyPrefix = "__appkey__";
    private String namespacePrefix = "__namespace__";

    @Autowired
    private ApplicationService applicationService;
    @Autowired
    private PersistentObjectDAO persistentObjectDAO;

    @Value("#{dbConfig.obj_persistent_db}")
    private String dbname;
    @Value("#{dbConfig.obj_persistent_collection}")
    private String collectionName;

    private IDataConverter dataConverter = new BasicEntityConvert();

    @Override
    public IDataConverter getDataConverter() {
        return dataConverter;
    }

    @Override
    public void setDataConverter(IDataConverter converter) {
        this.dataConverter = converter;
    }

    @Override
    public ObjectId put(String appkey, String namespace, Document data) throws InvalidDataException {
        log.error("start put object into [{}, {}]", dbname, collectionName);
        applicationService.applicationExsist(appkey);

        try {
            ObjectId uuid = null;
            if (!data.containsKey("_id")) {
                uuid = ObjectId.get();
                data.put("_id", uuid);
            } else {
                data.getObjectId("_id");
            }
            data.put(appkeyPrefix, appkey);
            data.put(namespacePrefix, namespace);
            persistentObjectDAO.insertOne(dbname, collectionName, data);
            return uuid;
        } catch (BsonInvalidOperationException e) {
            if (log.isErrorEnabled()) {
                log.error("put object into persistent error, data is not JSON formart.=={}==", data);
            }
            throw new InvalidDataException(e);
        }
    }

    @Override
    public List<Document> get(String appkey, String namespace,
                              Bson filter, int skip, int limit,
                              List<String> includeFields,
                              List<String> excludeFields,
                              List<String> sortAsc,
                              List<String> sortDsc
    ) {
        applicationService.applicationExsist(appkey);
        try {
            ArrayList<Document> documents = new ArrayList<Document>();
            ArrayList<Bson> queryFilters = new ArrayList<Bson>();
            queryFilters.add(Filters.eq(namespacePrefix, namespace));
            queryFilters.add(Filters.eq(appkeyPrefix, appkey));
            if (null != filter) {
                queryFilters.add(filter);
            }
            FindIterable<Document> findIterable = persistentObjectDAO.find(
                    dbname, collectionName, Filters.and(queryFilters)
            );
            if (skip > 0) {
                findIterable.skip(skip);
            }
            if (limit > 0) {
                findIterable.limit(limit);
            }
            if (null != includeFields && includeFields.size() > 0) {
                findIterable.projection(Projections.include(includeFields));
            }
            if (null == excludeFields) {
                excludeFields = new ArrayList<String>();
            }
            excludeFields.add(appkeyPrefix);
            excludeFields.add(namespacePrefix);
            findIterable.projection(Projections.exclude(excludeFields));
            ArrayList<Bson> sortBson = new ArrayList<Bson>();
            if (null != sortAsc && sortAsc.size() > 0) {
                sortBson.add(Sorts.ascending(sortAsc));
            }
            if (null != sortDsc && sortDsc.size() > 0) {
                sortBson.add(Sorts.ascending(sortDsc));
            }
            if (sortBson.size() > 0) {
                findIterable.sort(Sorts.orderBy(sortBson));
            }

            return findIterable.into(documents);
        } catch (BsonInvalidOperationException e) {
            if (log.isErrorEnabled()) {
                log.error("get error. Query:{}", filter);
            }
            throw new InvalidDataException(e);
        }
    }

    @Override
    public long remove(String appkey, String namespace, Bson filter) {
        DeleteResult result = persistentObjectDAO.deleteMany(dbname, collectionName,
                Filters.and(filter,
                        Filters.eq(namespacePrefix, namespace),
                        Filters.eq(appkeyPrefix, appkey)));
        return result.getDeletedCount();
    }

    @Override
    public long replace(String appkey, String namespace, String id, Document data) {
        applicationService.applicationExsist(appkey);
        UpdateResult result = persistentObjectDAO.replaceOne(dbname, collectionName,
                Filters.and(Filters.eq(namespacePrefix, namespace),
                        Filters.eq(appkeyPrefix, appkey),
                        Filters.eq("_id", new ObjectId(id))
                ), data);
        return result.getModifiedCount();
    }

    @Override
    public long update(String appkey, String namespace, Document filter, Document updates) {
        applicationService.applicationExsist(appkey);
        UpdateResult result = persistentObjectDAO.updateMany(dbname, collectionName,
                Filters.and(Filters.eq(namespacePrefix, namespace),
                        Filters.eq(appkeyPrefix, appkey),
                        filter
                ), updates);
        return result.getModifiedCount();
    }

    @Override
    public long updateOne(String appkey, String namespace, String id, Document filter, Document updates) {
        applicationService.applicationExsist(appkey);
        ArrayList<Bson> updateFilters = new ArrayList<Bson>();
        updateFilters.add(Filters.eq(namespacePrefix, namespace));
        updateFilters.add(Filters.eq(appkeyPrefix, appkey));
        updateFilters.add(Filters.eq("_id", new ObjectId(id)));
        if (null != filter) {
            updateFilters.add(filter);
        }
        UpdateResult result = persistentObjectDAO.replaceOne(dbname, collectionName,
                Filters.and(updateFilters), updates);
        return result.getModifiedCount();
    }

    @Override
    public long deleteOne(String appkey, String namespace, String id) {
        applicationService.applicationExsist(appkey);
        DeleteResult result = persistentObjectDAO.deleteOne(dbname, collectionName,
                Filters.and(Filters.eq(namespacePrefix, namespace),
                        Filters.eq(appkeyPrefix, appkey),
                        Filters.eq("_id", new ObjectId(id))
                ));
        return result.getDeletedCount();
    }

    @Override
    public long delete(String appkey, String namespace, Bson filter) {
        applicationService.applicationExsist(appkey);
        DeleteResult result = persistentObjectDAO.deleteMany(dbname, collectionName,
                Filters.and(Filters.eq(namespacePrefix, namespace),
                        Filters.eq(appkeyPrefix, appkey),
                        filter
                ));
        return result.getDeletedCount();
    }

    @Override
    public BulkWirteResultModel bulk(String appkey, String namespace, String json) {
        applicationService.applicationExsist(appkey);
        BulkWrite write = BulkWrite.fromJson(json);
        List<WriteModel<Document>> writeModels = write.getWriteModelsWithFilter(Filters.and(
                Filters.and(Filters.eq(namespacePrefix, namespace),
                        Filters.eq(appkeyPrefix, appkey)
                )));
        BulkWriteResult result = persistentObjectDAO.bulkWrite(dbname, collectionName, writeModels);
        return new BulkWirteResultModel(
                result.getDeletedCount(),
                result.getInsertedCount(),
                result.getMatchedCount(),
                result.getModifiedCount()
        );
    }
}
