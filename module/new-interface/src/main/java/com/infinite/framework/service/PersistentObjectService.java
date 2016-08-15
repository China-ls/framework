package com.infinite.framework.service;

import com.infinite.framework.service.exception.InvalidDataException;
import com.infinite.framework.service.persistent.obj.IDataConverter;
import org.bson.Document;

/**
 * @author by hx on 16-7-25.
 * @since 1.0
 */
public interface PersistentObjectService {

    public void setDataConverter(IDataConverter converter);

    public IDataConverter getDataConverter();

    public void put(String appkey, String namespace, String data) throws InvalidDataException;

    public Document get(String appkey, String namespace, String query);

    public int remove(String appkey, String namespace, String filter);

}
