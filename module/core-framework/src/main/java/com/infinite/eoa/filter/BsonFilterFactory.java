package com.infinite.eoa.filter;

import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.HashMap;
import java.util.Map;

/**
 * Create IBsonFilter by filterMapping and VirtualSensorFN FilterConfig
 * @see org.springframework.context.ApplicationContextAware
 * @author Created by hx on 16-7-25.
 * @since 1.0
 */
public class BsonFilterFactory implements ApplicationContextAware {
    private static final Logger log = LoggerFactory.getLogger(BsonFilterFactory.class);

    private HashMap<String, Class<? extends IBsonFilter>> filterMapping = new HashMap<String, Class<? extends IBsonFilter>>(0);
    private ApplicationContext context;

    public HashMap<String, Class<? extends IBsonFilter>> getFilterMapping() {
        return filterMapping;
    }

    public void setFilterMapping(HashMap<String, Class<? extends IBsonFilter>> filterMapping) {
        this.filterMapping = filterMapping;
    }

    public IBsonFilterChain createFilterChain(Document data, Map<String, Document> config) {
        if (filterMapping.isEmpty()) {
            if (log.isErrorEnabled()) {
                log.error("filterMapping is EMPTY");
            }
            throw new IllegalStateException("You shoud set up FilterMapping to init filter chain.");
        }
        IBsonFilterChain chain = new SimpleBsonFilterChain();
        chain.setBsonData(data);

        for (Map.Entry<String, Document> entry : config.entrySet()) {
            Class<? extends IBsonFilter> mappedClass = filterMapping.get(entry.getKey());
            if (null == mappedClass) {
                if (log.isErrorEnabled()) {
                    log.warn("could not find a mappedClass for key : {}", entry.getKey());
                    continue;
                }
                IBsonFilter filter = null;
                if (null != context) {
                    filter = context.getBean(mappedClass);
                }
                if (null == filter) {
                    try {
                        filter = mappedClass.newInstance();
                    } catch (Exception e) {
                        log.warn("could not instance a mappedClass for key : {}, class: {}.\n{}",
                                entry.getKey(), mappedClass.getSimpleName(), e);
                    }
                }
                if (null != filter) {
                    filter.setBsonConfig(entry.getValue());
                    chain.addFilter(filter);
                }
            }
        }

        return chain;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}
