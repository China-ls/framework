package com.infinite.framework.bson.filter.bulk;

import com.mongodb.client.model.ReplaceOneModel;
import com.mongodb.client.model.WriteModel;
import org.bson.Document;
import org.bson.conversions.Bson;

public class ReplaceOne extends AbstractBulkModel {
    private Document replacement;

    public ReplaceOne() {
    }

    public ReplaceOne(Bson filter,Document replacement) {
        this.filter = filter;
        this.replacement = replacement;
    }

    public Document toDocument() {
        return new Document("filter", filter).append("replacement", replacement);
    }

    public AbstractBulkModel fromDocument(Document document) {
        this.filter = document.get("filter", Document.class);
        this.replacement = document.get("replacement", Document.class);
        return this;
    }

    public Document getReplacement() {
        return replacement;
    }

    public void setReplacement(Document replacement) {
        this.replacement = replacement;
    }

    public WriteModel convertToWriteModel() {
        return new ReplaceOneModel(filter, replacement);
    }

    public AbstractBulkModel appendDocument(Document document) {
        this.replacement.putAll(document);
        return this;
    }
}
