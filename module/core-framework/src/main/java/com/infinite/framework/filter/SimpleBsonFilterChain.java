package com.infinite.framework.filter;

import org.bson.Document;

import java.util.Iterator;

public class SimpleBsonFilterChain extends AbstractBsonFilterChain {

    public Document doFilter(Document message, Document latestFilter) {
        Iterator<IBsonFilter> filtersIterator = getFiltersIterator();

        Document result = null;

        while (filtersIterator.hasNext()) {
            IBsonFilter filter = filtersIterator.next();
            result = filter.doFilter(message, result);
        }

        return result;
    }

}
