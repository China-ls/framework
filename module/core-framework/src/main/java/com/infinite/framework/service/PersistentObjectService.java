package com.infinite.framework.service;

import com.infinite.framework.service.entity.BulkWirteResultModel;
import com.infinite.framework.service.exception.InvalidDataException;
import com.infinite.framework.service.persistent.obj.IDataConverter;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.List;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface PersistentObjectService {

    public void setDataConverter(IDataConverter converter);

    public IDataConverter getDataConverter();

    public ObjectId put(String appkey, String namespace, Document data) throws InvalidDataException;

    public List<Document> get(String appkey, String namespace,
                              Bson filter, int skip, int limit,
                              List<String> includeFields,
                              List<String> excludeFields,
                              List<String> sortAsc,
                              List<String> sortDsc);

    public long remove(String appkey, String namespace, Bson filter);

    long replace(String appkey, String namespace, String id, Document data);

    long update(String appkey, String namespace, Document filter, Document updates);

    long updateOne(String appkey, String namespace, String id, Document filter, Document updates);

    long deleteOne(String appkey, String namespace, String id);

    long delete(String appkey, String namespace, Bson filter);

    BulkWirteResultModel bulk(String appkey, String namespace, String bson);
}
