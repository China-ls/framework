package com.ls.test.service;

import com.infinite.framework.core.util.JsonUtil;

public class OperatorBsonable<TItem> extends AbstractBsonable {
    private String fieldName;
    private Operator operator;

    public OperatorBsonable(final String operatorName, final String fieldName, final TItem value) {
        this.fieldName = notNull("fieldName", fieldName);
        this.operator = new Operator(operatorName, value);
    }

    private class Operator {
        private final String operatorName;
        private final TItem value;

        Operator(final String operatorName, final TItem value) {
            this.operatorName = notNull("operatorName", operatorName);
            this.value = value;
        }
    }

    @Override
    public String toJson() {
        return String.format("{%s:%s}", fieldName, JsonUtil.toJson(operator));
    }
}
