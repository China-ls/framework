package com.infinite.framework.filter;

import org.bson.Document;

import java.util.Iterator;

/**
 * FilterChain for IBsonFilter
 * @see com.infinite.framework.filter.IBsonFilter
 * @author hx on 16-7-25.
 */
public interface IBsonFilterChain extends IBsonFilter {

    public Document getBsonData();

    public void setBsonData(Document bson);

    public void removeFilter(IBsonFilter filter);

    public void addFilter(IBsonFilter filter);

    public Iterator<IBsonFilter> getFiltersIterator();

}
