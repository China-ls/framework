package com.infinite.framework.core.persistent;

import com.mongodb.MongoClient;
import com.mongodb.MongoNamespace;
import com.mongodb.ReadPreference;
import com.mongodb.bulk.BulkWriteResult;
import com.mongodb.client.*;
import com.mongodb.client.model.*;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.mongodb.morphia.Datastore;

import java.util.List;

/**
 * Created by hx on 16-7-5.
 */
public interface IMongoDAO {
    public MongoClient getMongo();

    public Datastore getDatastore();

    public void setMongo(MongoClient mongo);

    public MongoDatabase getDatabase(String databaseName);

    public void createCollection(MongoDatabase database, String collectionName);

    public void createCollection(String databaseName, String collectionName);

    public void createCollection(MongoDatabase database, String collectionName, CreateCollectionOptions createCollectionOptions);

    public void createCollection(String databaseName, String collectionName, CreateCollectionOptions createCollectionOptions);

    public MongoCollection<Document> getCollection(MongoDatabase database, String collectionName);

    public MongoCollection<Document> getCollection(String databaseName, String collectionName);

    public MongoCollection<Document> getCollection(MongoDatabase database, String collectionName, Class<Document> documentClass);

    public MongoCollection<Document> getCollection(String databaseName, String collectionName, Class<Document> documentClass);

    public ListCollectionsIterable<Document> listCollections(MongoDatabase database);

    public ListCollectionsIterable<Document> listCollections(String databaseName);

    public void dropDatabase(MongoDatabase database);

    public void dropDatabase(String databaseName);

    public MongoIterable<String> listCollectionNames(MongoDatabase database);

    public MongoIterable<String> listCollectionNames(String databaseName);

    public Document findFirst(String databaseName, String collectionName);

    public Document findFirst(MongoDatabase database, String collectionName);

    public Document findFirst(MongoCollection<Document> collection);

    public Document findFirst(String databaseName, String collectionName, Bson filter);

    public Document findFirst(MongoDatabase database, String collectionName, Bson filter);

    public Document findFirst(MongoCollection<Document> collection, Bson filter);

    public FindIterable<Document> find(String databaseName, String collectionName);

    public FindIterable<Document> find(MongoDatabase database, String collectionName);

    public FindIterable<Document> find(MongoCollection<Document> collection);

    public FindIterable<Document> find(String databaseName, String collectionName, Bson filter);

    public FindIterable<Document> find(MongoDatabase database, String collectionName, Bson filter);

    public FindIterable<Document> find(MongoCollection<Document> collection, Bson filter);

    public Document findOneAndDelete(String databaseName, String collectionName, Bson filter);

    public Document findOneAndDelete(MongoDatabase database, String collectionName, Bson filter);

    public Document findOneAndDelete(MongoCollection<Document> collection, Bson filter);

    public Document findOneAndDelete(String databaseName, String collectionName, Bson filter, FindOneAndDeleteOptions options);

    public Document findOneAndDelete(MongoDatabase database, String collectionName, Bson filter, FindOneAndDeleteOptions options);

    public Document findOneAndDelete(MongoCollection<Document> collection, Bson filter, FindOneAndDeleteOptions options);

    public Document findOneAndReplace(String databaseName, String collectionName, Bson filter, Document replacement);

    public Document findOneAndReplace(MongoDatabase database, String collectionName, Bson filter, Document replacement);

    public Document findOneAndReplace(MongoCollection<Document> collection, Bson filter, Document replacement);

    public Document findOneAndReplace(String databaseName, String collectionName, Bson filter, Document replacement, FindOneAndReplaceOptions options);

    public Document findOneAndReplace(MongoDatabase database, String collectionName, Bson filter, Document replacement, FindOneAndReplaceOptions options);

    public Document findOneAndReplace(MongoCollection<Document> collection, Bson filter, Document replacement, FindOneAndReplaceOptions options);

    public Document findOneAndUpdate(String databaseName, String collectionName, Bson filter, Document replacement);

    public Document findOneAndUpdate(MongoDatabase database, String collectionName, Bson filter, Document replacement);

    public Document findOneAndUpdate(MongoCollection<Document> collection, Bson filter, Document replacement);

    public Document findOneAndUpdate(String databaseName, String collectionName, Bson filter, Document replacement, FindOneAndUpdateOptions options);

    public Document findOneAndUpdate(MongoDatabase database, String collectionName, Bson filter, Document replacement, FindOneAndUpdateOptions options);

    public Document findOneAndUpdate(MongoCollection<Document> collection, Bson filter, Document replacement, FindOneAndUpdateOptions options);

    public void drop(String databaseName, String collectionName);

    public void drop(MongoDatabase database, String collectionName);

    public void insertOne(String databaseName, String collectionName, Document document);

    public void insertOne(MongoDatabase database, String collectionName, Document document);

    public void insertMany(String databaseName, String collectionName, List<? extends Document> documents);

    public void insertMany(MongoDatabase database, String collectionName, List<? extends Document> documents);

    public void insertMany(String databaseName, String collectionName, List<? extends Document> documents, InsertManyOptions options);

    public void insertMany(MongoDatabase database, String collectionName, List<? extends Document> documents, InsertManyOptions options);

    public DeleteResult deleteOne(MongoDatabase database, String collectionName, Bson filter);

    public DeleteResult deleteOne(String databaseName, String collectionName, Bson filter);

    public DeleteResult deleteMany(MongoDatabase database, String collectionName, Bson filter);

    public DeleteResult deleteMany(String databaseName, String collectionName, Bson filter);

    public UpdateResult replaceOne(MongoDatabase database, String collectionName, Bson filter, Document replacement);

    public UpdateResult replaceOne(String databaseName, String collectionName, Bson filter, Document document);

    public UpdateResult replaceOne(MongoDatabase database, String collectionName, Bson filter, Document replacement, UpdateOptions updateOptions);

    public UpdateResult replaceOne(String databaseName, String collectionName, Bson filter, Document replacement, UpdateOptions updateOptions);

    public UpdateResult updateOne(MongoDatabase database, String collectionName, Bson filter, Document update);

    public UpdateResult updateOne(String databaseName, String collectionName, Bson filter, Document update);

    public UpdateResult updateOne(MongoDatabase database, String collectionName, Bson filter, Document update, UpdateOptions updateOptions);

    public UpdateResult updateOne(String databaseName, String collectionName, Bson filter, Document update, UpdateOptions updateOptions);

    public UpdateResult updateMany(MongoDatabase database, String collectionName, Bson filter, Document update);

    public UpdateResult updateMany(String databaseName, String collectionName, Bson filter, Document update);

    public UpdateResult updateMany(MongoDatabase database, String collectionName, Bson filter, Document update, UpdateOptions updateOptions);

    public UpdateResult updateMany(String databaseName, String collectionName, Bson filter, Document update, UpdateOptions updateOptions);

    public String createIndex(MongoDatabase database, String collectionName, Bson keys);

    public String createIndex(String databaseName, String collectionName, Bson keys);

    public String createIndex(MongoDatabase database, String collectionName, Bson keys, IndexOptions indexOptions);

    public String createIndex(String databaseName, String collectionName, Bson keys, IndexOptions indexOptions);

    public List<String> createIndexes(MongoDatabase database, String collectionName, List<IndexModel> indexes);

    public List<String> createIndexes(String databaseName, String collectionName, List<IndexModel> indexes);

    public ListIndexesIterable<Document> listIndexes(MongoDatabase database, String collectionName);

    public ListIndexesIterable<Document> listIndexes(String databaseName, String collectionName);

    public void dropIndex(MongoDatabase database, String collectionName, String indexName);

    public void dropIndex(String databaseName, String collectionName, String indexName);

    public void dropIndex(MongoDatabase database, String collectionName, Bson keys);

    public void dropIndex(String databaseName, String collectionName, Bson keys);

    public void dropIndexes(MongoDatabase database, String collectionName);

    public void dropIndexes(String databaseName, String collectionName);

    public void renameCollection(MongoDatabase database, String collectionName, MongoNamespace newCollectionNamespace);

    public void renameCollection(String databaseName, String collectionName, MongoNamespace newCollectionNamespace);

    public void renameCollection(MongoDatabase database, String collectionName, MongoNamespace newCollectionNamespace, RenameCollectionOptions renameCollectionOptions);

    public void renameCollection(String databaseName, String collectionName, MongoNamespace newCollectionNamespace, RenameCollectionOptions renameCollectionOptions);

    public MongoIterable<String> listDatabaseNames();

    public ListDatabasesIterable<Document> listDatabases();

    public void runCommand(String databaseName, Bson command);

    public void runCommand(MongoDatabase database, Bson command);

    public void runCommand(MongoDatabase database, Bson command, ReadPreference readPreference);

    public void runCommand(String databaseName, Bson command, ReadPreference readPreference);

    public AggregateIterable<Document> aggregate(String databaseName, String collectionName, List<? extends Bson> pipeline);

    public AggregateIterable<Document> aggregate(MongoDatabase database, String collectionName, List<? extends Bson> pipeline);

    public AggregateIterable<Document> aggregate(MongoCollection<Document> mongoCollection, List<? extends Bson> pipeline);

    public MapReduceIterable<Document> mapReduce(MongoDatabase database, String collectionName, String mapFunction, String reduceFunction);

    public MapReduceIterable<Document> mapReduce(String databaseName, String collectionName, String mapFunction, String reduceFunction);

    public MapReduceIterable<Document> mapReduce(MongoCollection<Document> mongoCollection, String mapFunction, String reduceFunction);

    public BulkWriteResult bulkWrite(String databaseName, String collectionName, List<? extends WriteModel<? extends Document>> requests);

    public BulkWriteResult bulkWrite(MongoDatabase database, String collectionName, List<? extends WriteModel<? extends Document>> requests);

    public long count(MongoDatabase database, String collectionName);

    public long count(String databaseName, String collectionName);

    public long count(String databaseName, String collectionName, Bson filter);

    public long count(MongoDatabase database, String collectionName, Bson filter);

    public long count(MongoCollection<Document> mongoCollection, Bson filter);

    public long count(MongoDatabase database, String collectionName, Bson filter, CountOptions options);

    public long count(String databaseName, String collectionName, Bson filter, CountOptions options);
}
