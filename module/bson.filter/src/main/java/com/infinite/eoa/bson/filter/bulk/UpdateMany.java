package com.infinite.eoa.bson.filter.bulk;

import com.mongodb.client.model.UpdateManyModel;
import com.mongodb.client.model.WriteModel;
import org.bson.Document;
import org.bson.conversions.Bson;

public class UpdateMany extends AbstractBulkModel {
    private Bson update;

    public UpdateMany() {
    }

    public UpdateMany(Bson filter, Bson update) {
        this.filter = filter;
        this.update = update;
    }

    public Document toDocument() {
        return new Document("filter", filter).append("update", update);
    }

    public AbstractBulkModel fromDocument(Document document) {
        this.filter = document.get("filter", Document.class);
        this.update = document.get("update", Document.class);
        return this;
    }

    public Bson getUpdate() {
        return update;
    }

    public void setUpdate(Bson update) {
        this.update = update;
    }

    public WriteModel convertToWriteModel() {
        return new UpdateManyModel(filter, update);
    }

    public AbstractBulkModel appendDocument(Document document) {
        return this;
    }
}
