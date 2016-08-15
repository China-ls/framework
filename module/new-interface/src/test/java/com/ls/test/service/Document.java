package com.ls.test.service;

import com.infinite.framework.core.util.JsonUtil;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Document extends AbstractBsonable implements Map<String, Object>, Serializable {
    private final LinkedHashMap<String, Object> documentAsMap;

    public Document() {
        documentAsMap = new LinkedHashMap<String, Object>();
    }

    public Document(final String key, final Object value) {
        documentAsMap = new LinkedHashMap<String, Object>();
        documentAsMap.put(key, value);
    }

    public Document(final Map<String, Object> map) {
        documentAsMap = new LinkedHashMap<String, Object>(map);
    }

    public static Document parse(final String json) {
        return JsonUtil.fromJson(json, Document.class);
    }

    public Document append(final String key, final Object value) {
        documentAsMap.put(key, value);
        return this;
    }

    public <T> T get(final Object key, final Class<T> clazz) {
        return clazz.cast(documentAsMap.get(key));
    }

    public Integer getInteger(final Object key) {
        return (Integer) get(key);
    }

    public int getInteger(final Object key, final int defaultValue) {
        Object value = get(key);
        return value == null ? defaultValue : (Integer) value;
    }

    public Long getLong(final Object key) {
        return (Long) get(key);
    }

    public Double getDouble(final Object key) {
        return (Double) get(key);
    }

    public String getString(final Object key) {
        return (String) get(key);
    }

    public Boolean getBoolean(final Object key) {
        return (Boolean) get(key);
    }

    public boolean getBoolean(final Object key, final boolean defaultValue) {
        Object value = get(key);
        return value == null ? defaultValue : (Boolean) value;
    }

    public Date getDate(final Object key) {
        return (Date) get(key);
    }

    // Vanilla Map methods delegate to map field

    @Override
    public int size() {
        return documentAsMap.size();
    }

    @Override
    public boolean isEmpty() {
        return documentAsMap.isEmpty();
    }

    @Override
    public boolean containsValue(final Object value) {
        return documentAsMap.containsValue(value);
    }

    @Override
    public boolean containsKey(final Object key) {
        return documentAsMap.containsKey(key);
    }

    @Override
    public Object get(final Object key) {
        return documentAsMap.get(key);
    }

    @Override
    public Object put(final String key, final Object value) {
        return documentAsMap.put(key, value);
    }

    @Override
    public Object remove(final Object key) {
        return documentAsMap.remove(key);
    }

    @Override
    public void putAll(final Map<? extends String, ?> map) {
        documentAsMap.putAll(map);
    }

    @Override
    public void clear() {
        documentAsMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return documentAsMap.keySet();
    }

    @Override
    public Collection<Object> values() {
        return documentAsMap.values();
    }

    @Override
    public Set<Map.Entry<String, Object>> entrySet() {
        return documentAsMap.entrySet();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Document document = (Document) o;

        if (!documentAsMap.equals(document.documentAsMap)) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        return documentAsMap.hashCode();
    }

    @Override
    public String toString() {
        return "Document{"
                + documentAsMap
                + '}';
    }
}
