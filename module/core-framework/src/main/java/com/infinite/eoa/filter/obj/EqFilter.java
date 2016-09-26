package com.infinite.eoa.filter.obj;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see com.infinite.eoa.filter.IBsonFilter
 * @author hx on 16-7-25.
 */
public class EqFilter extends AbstractFilter {
    private static Logger log = LoggerFactory.getLogger(EqFilter.class);

    @Override
    public void doMatchField(String key, Object configValue, Object value) {
        if (configValue.equals(value)) {
            if (log.isDebugEnabled()) {
                log.debug("EQ: [{},{}]", key, value);
            }
        }
    }

    public static void main(String[] args) {
        Document document = new Document()
                .append("test1", 123)
                .append("test2", 12.2)
                .append("test3", true)
                .append("test", "ABC")
                .append("aaa", new Document("abc", "123"));
        Document config = new Document()
                .append("test1", 123)
                .append("aaa", new Document("abc", "123"));
        EqFilter filter = new EqFilter();
        filter.config = config;
        filter.doFilter(document, null);
    }
}
