package com.infinite.framework.bson.filter.bulk;

import com.mongodb.client.model.DeleteManyModel;
import com.mongodb.client.model.WriteModel;
import org.bson.Document;
import org.bson.conversions.Bson;

public class DeleteMany extends AbstractBulkModel {
    public DeleteMany(){}

    public DeleteMany(Bson filter) {
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
        return new DeleteManyModel(filter);
    }

    public AbstractBulkModel appendDocument(Document document) {
        return this;
    }
}
