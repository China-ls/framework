package com.infinite.water.entity.net;

import org.bson.Document;

import java.util.List;

/**
 * @author hx on 16-8-23.
 */
public class ListRequestResult extends BasicReuqestResult {

    private List<Document> obj;

    public List<Document> getObj() {
        return obj;
    }

    public void setObj(List<Document> obj) {
        this.obj = obj;
    }
}
