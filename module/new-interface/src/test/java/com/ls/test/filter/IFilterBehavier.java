package com.ls.test.filter;

/**
 * Created by hx on 16-7-15.
 */
public interface IFilterBehavier {
    public enum Behavier {
        EQ("="),
        LARGER(">"),
        LARGER_THAN(">="),
        ;

        public final String symbol;

        Behavier(String symbol) {
            this.symbol = symbol;
        }
    }

}
