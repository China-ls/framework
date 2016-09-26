package com.infinite.eoa.bson.filter.bulk;

import com.mongodb.client.model.DeleteOneModel;
import com.mongodb.client.model.WriteModel;
import org.bson.Document;
import org.bson.conversions.Bson;

public class DeleteOne extends AbstractBulkModel {
    public DeleteOne(){}

    public DeleteOne(Bson filter) {
        this.filter = filter;
    }

    public Document toDocument() {
        return new Document("filter", filter);
    }

    public AbstractBulkModel fromDocument(Document document) {
        this.filter = document.get("filter", Document.class);
        return this;
    }

    public WriteModel convertToWriteModel() {
        return new DeleteOneModel(filter);
    }

    public AbstractBulkModel appendDocument(Document document) {
        return this;
    }
}
