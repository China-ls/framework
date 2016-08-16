package com.infinite.framework.service.persistent.obj;

import org.bson.Document;

public interface IDataConverter {

    Document convert(String appkey, String namespace, String data);

}
