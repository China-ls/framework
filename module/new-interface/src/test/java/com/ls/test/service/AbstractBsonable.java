package com.ls.test.service;

import com.infinite.framework.core.util.JsonUtil;

import java.util.HashSet;
import java.util.Set;

public class AbstractBsonable implements IBsonable {
    @Override
    public String toJson() {
        return JsonUtil.toJson(this);
    }

    public static <T> T notNull(final String name, final T value) {
        if (value == null) {
            throw new IllegalArgumentException(name + " can not be null");
        }
        return value;
    }
}
