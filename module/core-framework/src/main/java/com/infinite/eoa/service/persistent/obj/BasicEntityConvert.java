package com.infinite.eoa.service.persistent.obj;

import com.infinite.eoa.core.util.DecriptUtil;
import org.bson.BsonInvalidOperationException;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class BasicEntityConvert implements IDataConverter {
    private static Logger log = LoggerFactory.getLogger(BasicEntityConvert.class);

    @Override
    public Document convert(String appkey, String namespace, String data) {
        try {
            Document document = Document.parse(data);
            String sha1 = DecriptUtil.SHA1(data);
            log.debug("sha1:{}", sha1);
            document.put("__appkey__", appkey);
            document.put("__namespace__", namespace);
            return document;
        } catch (BsonInvalidOperationException e) {
            if (log.isErrorEnabled()) {
                log.error("data convert error! Not JSON Format =={}==", data);
            }
            throw e;
        }
    }
}
