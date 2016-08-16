package com.infinite.framework.bson.filter;

public class SearchTextOptions {
    private String language;
    private Boolean caseSensitive;
    private Boolean diacriticSensitive;

    public String getLanguage() {
        return language;
    }

    public SearchTextOptions language(final String language) {
        this.language = language;
        return this;
    }

    public Boolean getCaseSensitive() {
        return caseSensitive;
    }

    public SearchTextOptions caseSensitive(final Boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
        return this;
    }

    public Boolean getDiacriticSensitive() {
        return diacriticSensitive;
    }

    public SearchTextOptions diacriticSensitive(final Boolean diacriticSensitive) {
        this.diacriticSensitive = diacriticSensitive;
        return this;
    }
}
