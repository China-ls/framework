package com.infinite.water.entity.net;

import org.bson.Document;

/**
 * @author hx on 16-8-23.
 */
public class ObjectRequestResult extends BasicReuqestResult {

    private Document obj;

    public Document getObj() {
        return obj;
    }

    public void setObj(Document obj) {
        this.obj = obj;
    }
}
