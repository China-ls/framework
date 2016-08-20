package com.infinite.framework.service;

import com.infinite.framework.service.exception.InvalidDataException;
import com.infinite.framework.service.persistent.obj.IDataConverter;
import org.bson.Document;
import org.bson.conversions.Bson;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface PersistentObjectService {

    public void setDataConverter(IDataConverter converter);

    public IDataConverter getDataConverter();

    public void put(String appkey, String namespace, Document data) throws InvalidDataException;

    public Document get(String appkey, String namespace, Bson filter);

    public long remove(String appkey, String namespace, Bson filter);

}
