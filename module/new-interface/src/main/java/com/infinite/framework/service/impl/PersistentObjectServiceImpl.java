package com.infinite.framework.service.impl;

import com.infinite.framework.persistent.PersistentObjectDAO;
import com.infinite.framework.service.PersistentObjectService;
import com.infinite.framework.service.exception.InvalidDataException;
import com.infinite.framework.service.persistent.obj.BasicEntityConvert;
import com.infinite.framework.service.persistent.obj.IDataConverter;
import com.mongodb.client.model.Filters;
import org.bson.BsonInvalidOperationException;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author by hx on 16-8-9.
 * @since 1.0
 */
@Service("PersistentObjectService")
public class PersistentObjectServiceImpl implements PersistentObjectService {
    private static final Logger log = LoggerFactory.getLogger(PersistentObjectService.class);

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
    public void put(String appkey, String namespace, String data) throws InvalidDataException {
        log.error("start put object into [{}, {}]", dbname, collectionName);

        try {
            Document document = dataConverter.convert(appkey, namespace, data);
            persistentObjectDAO.insertOne(dbname, collectionName, document);
        } catch (BsonInvalidOperationException e) {
            if (log.isErrorEnabled()) {
                log.error("put object into persistent error, data is not JSON formart.=={}==", data);
            }
            throw new InvalidDataException(e);
        }
    }

    @Override
    public Document get(String appkey, String namespace, String query) {
        try {
            return persistentObjectDAO.findFirst(dbname, collectionName, Document.parse(query));
        } catch (BsonInvalidOperationException e) {
            if (log.isErrorEnabled()) {
                log.error("get error. Query:{}", query);
            }
            throw new InvalidDataException(e);
        }
    }

    @Override
    public int remove(String appkey, String namespace, String filter) {
        persistentObjectDAO.deleteMany(dbname, collectionName, Filters.and(
        ));
        return 0;
    }
}
