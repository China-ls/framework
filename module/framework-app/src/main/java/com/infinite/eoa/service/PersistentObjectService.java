package com.infinite.eoa.service;

import com.infinite.eoa.service.entity.BulkWirteResultModel;
import com.infinite.eoa.service.exception.InvalidDataException;
import com.infinite.eoa.service.persistent.obj.IDataConverter;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface PersistentObjectService {

    public void setDataConverter(IDataConverter converter);

    public IDataConverter getDataConverter();

    public ObjectId put(String appkey, String namespace, Document data) throws InvalidDataException;

    public Document getOne(String appkey, String namespace,
                           Bson filter, int skip, List<String> includeFields,
                           List<String> excludeFields,
                           List<String> sortAsc,
                           List<String> sortDsc);

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

    long deleteOneBulk(String appkey, ArrayList<String> namespace, ArrayList<Document> filters);

    long deleteManyBulk(String appkey, ArrayList<String> namespace, ArrayList<Document> filters);

    BulkWirteResultModel bulk(String appkey, ArrayList<String> namespace, String bson);
}
