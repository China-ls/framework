package com.infinite.eoa.bson.filter.bulk;

import com.infinite.eoa.bson.filter.PersistentObjectFilters;
import com.infinite.eoa.bson.filter.PersistentObjectUpdates;
import com.mongodb.MongoClient;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.WriteModel;
import org.bson.BsonDocument;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.ArrayList;
import java.util.List;

public class BulkWrite {
    private ArrayList<Document> models = new ArrayList<Document>(0);

    public BulkWrite andUpdateOne(PersistentObjectFilters filter, Bson update) {
        return andUpdateOne(filter.getDocument(), PersistentObjectUpdates.combineToDocument(update));
    }

    public BulkWrite andUpdateMany(PersistentObjectFilters filter, Bson update) {
        return andUpdateMany(filter.getDocument(), PersistentObjectUpdates.combineToDocument(update));
    }

    public BulkWrite andReplaceOne(PersistentObjectFilters filter, Document replacement) {
        return andReplaceOne(filter.getDocument(), replacement);
    }

    public BulkWrite andDeleteOne(PersistentObjectFilters filter) {
        return andDeleteOne(filter.getDocument());
    }

    public BulkWrite andDeleteMany(PersistentObjectFilters filter) {
        return andDeleteMany(filter.getDocument());
    }

    //********************//

    public BulkWrite andUpdateOne(Bson filter, Bson update) {
        addToModels(Operator.UPDATE, new UpdateOne(filter, update));
        return this;
    }

    public BulkWrite andUpdateMany(Bson filter, Bson update) {
        addToModels(Operator.UPDATE_MANY, new UpdateMany(filter, update));
        return this;
    }

    public BulkWrite andReplaceOne(Bson filter, Document replacement) {
        addToModels(Operator.REPLACE, new ReplaceOne(filter, replacement));
        return this;
    }

    public BulkWrite andDeleteOne(Bson filter) {
        addToModels(Operator.DELETE, new DeleteOne(filter));
        return this;
    }

    public BulkWrite andDeleteMany(Bson filter) {
        addToModels(Operator.DELETE_MANY, new DeleteMany(filter));
        return this;
    }

    public BulkWrite andInsertOne(Document filter) {
        addToModels(Operator.ADD, new InsertOne(filter));
        return this;
    }

    private void addToModels(Operator operator, AbstractBulkModel model) {
        models.add(
                new Document("name", operator.name()).append("value", model.toDocument())
        );
    }

    private void addToModels(String operatorName, Document model) {
        models.add(new Document("name", operatorName).append("value", model));
    }

    public List<WriteModel> getWriteModels() {
        ArrayList<WriteModel> list = new ArrayList<WriteModel>();
        for (Document document : models) {
            WriteModel model = convert(document);
            if (null != model) {
                list.add(model);
            }
        }
        return list;
    }

    public List<WriteModel<Document>> getWriteModelsWithFilter(Bson filter) {
        ArrayList<WriteModel<Document>> list = new ArrayList<WriteModel<Document>>();
        BsonDocument filterDocument = filter.toBsonDocument(BsonDocument.class,
                MongoClient.getDefaultCodecRegistry());
        for (Document document : models) {
            WriteModel model = convertAndAppendFilter(document, filterDocument);
            if (null != model) {
                list.add(model);
            }
        }
        return list;
    }

    public List<WriteModel<Document>> getWriteModelsWithAppendDocument(Document documentAppend) {
        ArrayList<WriteModel<Document>> list = new ArrayList<WriteModel<Document>>();
        for (Document document : models) {
            WriteModel model = convertAndAppendDocument(document, documentAppend);
            if (null != model) {
                list.add(model);
            }
        }
        return list;
    }

    public List<WriteModel<Document>> getWriteModelsWithAppendDocuments(List<Document> documentAppends) {
        ArrayList<WriteModel<Document>> list = new ArrayList<WriteModel<Document>>();
        int i = 0;
        for (Document document : models) {
            Document documentAppend = documentAppends.size() > i ? documentAppends.get(i) : null;
            WriteModel model = convertAndAppendDocument(document, documentAppend);
            if (null != model) {
                list.add(model);
            }
            i++;
        }
        return list;
    }

    private WriteModel convert(Document document) {
        AbstractBulkModel model = convertToBulkModel(document);
        return null == model ? null : model.convertToWriteModel();
    }

    private AbstractBulkModel convertToBulkModel(Document document) {
        Operator operator = Operator.valueOf(document.getString("name"));
        Document value = (Document) document.get("value");
        AbstractBulkModel model = null;
        switch (operator) {
            case ADD:
                model = new InsertOne();
                break;
            case UPDATE:
                model = new UpdateOne();
                break;
            case UPDATE_MANY:
                model = new UpdateMany();
                break;
            case REPLACE:
                model = new ReplaceOne();
                break;
            case DELETE:
                model = new DeleteOne();
                break;
            case DELETE_MANY:
                model = new DeleteMany();
                break;
        }
        return null == model ? null : model.fromDocument(value);
    }

    private WriteModel convertAndAppendFilter(Document document, BsonDocument filter) {
        AbstractBulkModel model = convertToBulkModel(document);
        return null == model ? null : model.appendFilter(filter).convertToWriteModel();
    }

    private WriteModel convertAndAppendDocument(Document document, Document append) {
        AbstractBulkModel model = convertToBulkModel(document);
        return null == model ? null : model.appendDocument(append).convertToWriteModel();
    }

    public String toJson() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        int i = 0, size = models.size();
        for (Document document : models) {
            stringBuilder.append(document.toJson());
            if (i != size - 1) {
                stringBuilder.append(",");
            }
            i++;
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static BulkWrite fromJson(String json) {
        BulkWrite write = new BulkWrite();
        json = "{array:" + json + "}";
        Document document = Document.parse(json);
        ArrayList<Document> array = (ArrayList<Document>) document.get("array");
        for (Document d : array) {
            write.addToModels(d.getString("name"), (Document) d.get("value"));
        }
        return write;
    }

    public static void main(String[] args) {
        BulkWrite write = new BulkWrite();
        write.andUpdateOne(
                PersistentObjectFilters.eq("id", "asdf"),
                PersistentObjectUpdates.set("name", "aaa")
        )
                .andDeleteOne(PersistentObjectFilters.eq("id", "123"));
        String json = write.toJson();
        System.out.println(json);

        System.out.println("==================");

        System.out.println(BulkWrite.fromJson(json).getWriteModelsWithFilter(
                Filters.and(
                        Filters.eq("a", "aaa"),
                        Filters.eq("b", "aaa1")
                )
        ));
    }
}
