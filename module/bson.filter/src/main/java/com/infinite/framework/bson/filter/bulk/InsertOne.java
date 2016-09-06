package com.infinite.framework.bson.filter.bulk;

import com.mongodb.client.model.InsertOneModel;
import com.mongodb.client.model.WriteModel;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

public class InsertOne extends AbstractBulkModel {
    private Document document;

    public InsertOne() {
    }

    public InsertOne(Document document) {
        this.document = document;
    }

    public Document toDocument() {
        return new Document("document", document);
    }

    public AbstractBulkModel appendFilter(BsonDocument filter) {
        return this;
    }

    public AbstractBulkModel fromDocument(Document document) {
        this.document = document.get("document", Document.class);
        return this;
    }

    public WriteModel convertToWriteModel() {
        return new InsertOneModel<Bson>(document);
    }

    public AbstractBulkModel appendDocument(Document document) {
        this.document.putAll(document);
        return this;
    }
}
