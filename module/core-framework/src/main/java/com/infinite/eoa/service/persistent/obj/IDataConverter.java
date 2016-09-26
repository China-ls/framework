package com.infinite.eoa.service.persistent.obj;

import org.bson.Document;

public interface IDataConverter {

    Document convert(String appkey, String namespace, String data);

}
