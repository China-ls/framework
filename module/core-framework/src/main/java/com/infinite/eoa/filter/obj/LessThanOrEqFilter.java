package com.infinite.eoa.filter.obj;

import org.apache.commons.lang.math.NumberUtils;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @see com.infinite.eoa.filter.IBsonFilter
 * @author hx on 16-7-25.
 */
public class LessThanOrEqFilter extends AbstractFilter {
    private static Logger log = LoggerFactory.getLogger(LessThanFilter.class);

    @Override
    public void doMatchField(String key, Object configValue, Object value) {
        double config = NumberUtils.toDouble(configValue.toString());
        double data = NumberUtils.toDouble(value.toString());
        if (data <= config) {
            if (log.isDebugEnabled()) {
                log.debug("LESS_THAN_OR_EQ: [{},{}]", key, value);
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
                .append("test1", 123);
        LessThanOrEqFilter filter = new LessThanOrEqFilter();
        filter.config = config;
        filter.doFilter(document, null);
    }
}
