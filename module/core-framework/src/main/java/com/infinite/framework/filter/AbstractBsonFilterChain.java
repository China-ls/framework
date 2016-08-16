package com.infinite.framework.filter;

import org.bson.Document;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by hx on 16-7-25.
 */
public abstract class AbstractBsonFilterChain implements IBsonFilterChain {
    protected ArrayList<IBsonFilter> filters = new ArrayList<IBsonFilter>(0);
    protected boolean isEnable = true;
    protected Document data;

    public Document getBsonData() {
        return data;
    }

    public void setBsonData(Document bson) {
        this.data = bson;
    }

    public Document getBsonConfig() {
        return null;
    }

    public void setBsonConfig(Document bson) {

    }

    public boolean isEnable() {
        return isEnable;
    }

    public void setEnable(boolean enable) {
        isEnable = enable;
    }

    public void removeFilter(IBsonFilter filter) {
        filters.remove(filter);
    }

    public void addFilter(IBsonFilter filter) {
        filters.add(filter);
    }

    public Iterator<IBsonFilter> getFiltersIterator() {
        return filters.iterator();
    }
}
