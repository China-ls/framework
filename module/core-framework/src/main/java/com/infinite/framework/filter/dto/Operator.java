package com.infinite.framework.filter.dto;

/**
 * Created by hx handler 16-6-15.
 */
public enum Operator  {
    EQUAL("="),

    NOT_EQUAL("!="),

    GREATER_THAN(">"),

    GREATER_THAN_OR_EQUAL(">="),

    LESS_THAN("<"),

    LESS_THAN_OR_EQUAL("<="),

    NOT("not"),

    SIZE("size"),

    IN("in"),

    NOT_IN("nin"),

    ALL("all");


    private String op;

    Operator(String op) {
        this.op = op;
    }

    public String op() {
        return this.op;
    }

    public static Operator fromString(String op) {
        for (int i = 0; i < values().length; i++) {
            final Operator operator = values()[i];
            if (operator.sameAs(op)) {
                return operator;
            }
        }
        return null;
    }

    private boolean sameAs(final String val) {
        return op.equals(val);
    }
}
