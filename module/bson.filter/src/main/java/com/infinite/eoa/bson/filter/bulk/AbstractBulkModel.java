package com.infinite.eoa.bson.filter.bulk;

import com.mongodb.MongoClient;
import com.mongodb.client.model.WriteModel;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

public abstract class AbstractBulkModel {
    protected Bson filter;

    public abstract Document toDocument();

    public abstract AbstractBulkModel fromDocument(Document document);

    public AbstractBulkModel appendFilter(BsonDocument filter) {
        BsonDocument filterDocument = filter.toBsonDocument(BsonDocument.class, MongoClient.getDefaultCodecRegistry());
        filter.putAll(filterDocument);
        this.filter = filter;
        return this;
    }

    public abstract AbstractBulkModel appendDocument(Document document);

    public abstract WriteModel convertToWriteModel();
}
