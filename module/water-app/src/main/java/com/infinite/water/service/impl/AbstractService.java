package com.infinite.water.service.impl;

import com.infinite.water.core.util.JsonUtil;
import com.infinite.water.entity.net.ListRequestResult;
import com.infinite.water.entity.net.NotSureRequestResult;
import com.infinite.water.entity.net.ObjectRequestResult;
import org.apache.commons.lang.StringUtils;
import org.bson.Document;

import java.util.List;

public class AbstractService {
    protected <T> T toJsonObject(String json, Class<T> type) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }
        return JsonUtil.fromJson(json, type);
    }

    protected Object getDocumentFromResponse(NotSureRequestResult requestResult) {
        if (null != requestResult && StringUtils.equals("0", requestResult.getCode())) {
            return requestResult.getObj();
        }
        return null;
    }

    protected List<Document> getDocumentFromResponse(ListRequestResult requestResult) {
        if (null != requestResult && StringUtils.equals("0", requestResult.getCode())) {
            return requestResult.getObj();
        }
        return null;
    }
    protected Document getDocumentFromResponse(ObjectRequestResult requestResult) {
        if (null != requestResult && StringUtils.equals("0", requestResult.getCode())) {
            return requestResult.getObj();
        }
        return null;
    }
}
