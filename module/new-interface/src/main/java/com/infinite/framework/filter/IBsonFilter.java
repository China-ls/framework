package com.infinite.framework.filter;

import org.bson.Document;

/**
 * Filters the VirtualSensorFN's data
 * @author hx handler 16-6-15.
 * @since 1.0
 */
public interface IBsonFilter {

    public Document getBsonConfig();

    public void setBsonConfig(Document bson);

    public boolean isEnable();

    public void setEnable(boolean enable);

    public Document doFilter(Document message, Document latestFilter);

}
