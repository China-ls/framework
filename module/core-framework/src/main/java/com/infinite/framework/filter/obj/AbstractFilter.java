package com.infinite.framework.filter.obj;

import com.infinite.framework.filter.IBsonFilter;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public abstract class AbstractFilter implements IBsonFilter {
    private static final Logger log = LoggerFactory.getLogger(AbstractFilter.class);

    protected Document data;
    protected Document config;
    protected boolean enable;

    public Document getBsonData() {
        return data;
    }

    public void setBsonData(Document bson) {
        this.data = bson;
    }

    public Document getBsonConfig() {
        return config;
    }

    public void setBsonConfig(Document bson) {
        this.config = bson;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    public Document doFilter(Document message, Document latestFilter) {
        Set<String> keySet = config.keySet();

        for (String key : keySet) {
            matchField(key, config, message);
        }
        return null;
    }

    protected void matchField(String key, Document config, Document data) {
        if (config.isEmpty()) {

        } else if (data.containsKey(key)) {
            Object configValue = config.get(key);
            Object value = data.get(key);
            Class configClass = configValue.getClass();
            Class dataClass = value.getClass();
            if (dataClass == configClass) {
                if (configClass == Document.class) {
                    Document documentConfig = (Document) configValue;
                    Document documentData = (Document) value;
                    Set<String> documentConfigKeySet = documentConfig.keySet();
                    for (String keyConfig : documentConfigKeySet) {
                        matchField(keyConfig, documentConfig, documentData);
                    }
                } else {
                    doMatchField(key, configValue, value);
                }
            } else {
                if (log.isWarnEnabled()) {
                    log.warn("Config key : {} values is type of {}, but data type is {}",
                            key, configClass.getSimpleName(), dataClass.getSimpleName());
                }
            }
        }
    }

    public abstract void doMatchField(String key, Object configValue, Object value);

}
