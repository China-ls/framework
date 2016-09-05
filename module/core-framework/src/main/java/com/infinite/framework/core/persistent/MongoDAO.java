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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created by hx on 16-7-4.
 */
public class MongoDAO implements IMongoDAO {
    private static Logger logger = LoggerFactory.getLogger(MongoDAO.class);

    protected MongoClient mongo;
    protected Datastore datastore;

    public MongoClient getMongo() {
        return mongo;
    }

    @Override
    public Datastore getDatastore() {
        return datastore;
    }

    public void setDatastore(Datastore datastore) {
        this.datastore = datastore;
    }

    public void setMongo(MongoClient mongo) {
        this.mongo = mongo;
    }

    public MongoDatabase getDatabase(String databaseName) {
        return mongo.getDatabase(databaseName);
    }

    public void createCollection(MongoDatabase database, String collectionName) {
        createCollection(database, collectionName, new CreateCollectionOptions());
    }

    public void createCollection(String databaseName, String collectionName) {
        createCollection(getDatabase(databaseName), collectionName);
    }

    public void createCollection(MongoDatabase database, String collectionName, CreateCollectionOptions createCollectionOptions) {
        database.createCollection(collectionName, createCollectionOptions);
    }

    public void createCollection(String databaseName, String collectionName, CreateCollectionOptions createCollectionOptions) {
        createCollection(getDatabase(databaseName), collectionName, createCollectionOptions);
    }

    public MongoCollection<Document> getCollection(MongoDatabase database, String collectionName) {
        return database.getCollection(collectionName);
    }

    public MongoCollection<Document> getCollection(String databaseName, String collectionName) {
        return getCollection(getDatabase(databaseName), collectionName);
    }

    public MongoCollection<Document> getCollection(MongoDatabase database, String collectionName, Class<Document> documentClass) {
        return database.getCollection(collectionName, documentClass);
    }

    public MongoCollection<Document> getCollection(String databaseName, String collectionName, Class<Document> documentClass) {
        return getCollection(getDatabase(databaseName), collectionName, documentClass);
    }

    public ListCollectionsIterable<Document> listCollections(MongoDatabase database) {
        return database.listCollections();
    }

    public ListCollectionsIterable<Document> listCollections(String databaseName) {
        return listCollections(getDatabase(databaseName));
    }

    public void dropDatabase(MongoDatabase database) {
        database.drop();
    }

    public void dropDatabase(String databaseName) {
        dropDatabase(getDatabase(databaseName));
    }

    public MongoIterable<String> listCollectionNames(MongoDatabase database) {
        return database.listCollectionNames();
    }

    public MongoIterable<String> listCollectionNames(String databaseName) {
        return listCollectionNames(getDatabase(databaseName));
    }

    public FindIterable<Document> find(String databaseName, String collectionName) {
        return find(getDatabase(databaseName), collectionName);
    }

    public FindIterable<Document> find(MongoDatabase database, String collectionName) {
        return find(getCollection(database, collectionName));
    }

    public FindIterable<Document> find(MongoCollection<Document> collection) {
        return collection.find();
    }

    public FindIterable<Document> find(String databaseName, String collectionName, Bson filter) {
        return find(getDatabase(databaseName), collectionName, filter);
    }

    public FindIterable<Document> find(MongoDatabase database, String collectionName, Bson filter) {
        return find(getCollection(database, collectionName), filter);
    }

    public FindIterable<Document> find(MongoCollection<Document> collection, Bson filter) {
        return collection.find(filter);
    }

    public Document findFirst(String databaseName, String collectionName) {
        return findFirst(getDatabase(databaseName), collectionName);
    }

    public Document findFirst(MongoDatabase database, String collectionName) {
        return findFirst(getCollection(database, collectionName));
    }

    public Document findFirst(MongoCollection<Document> collection) {
        return collection.find().first();
    }

    public Document findFirst(String databaseName, String collectionName, Bson filter) {
        return findFirst(getDatabase(databaseName), collectionName, filter);
    }

    public Document findFirst(MongoDatabase database, String collectionName, Bson filter) {
        return findFirst(getCollection(database, collectionName), filter);
    }

    public Document findFirst(MongoCollection<Document> collection, Bson filter) {
        return collection.find(filter).limit(1).first();
    }

    public Document findOneAndDelete(String databaseName, String collectionName, Bson filter) {
        return findOneAndDelete(getDatabase(databaseName), collectionName, filter);
    }

    public Document findOneAndDelete(MongoDatabase database, String collectionName, Bson filter) {
        return findOneAndDelete(getCollection(database, collectionName), filter);
    }

    public Document findOneAndDelete(MongoCollection<Document> collection, Bson filter) {
        return collection.findOneAndDelete(filter);
    }

    public Document findOneAndDelete(String databaseName, String collectionName, Bson filter, FindOneAndDeleteOptions options) {
        return findOneAndDelete(getDatabase(databaseName), collectionName, filter, options);
    }

    public Document findOneAndDelete(MongoDatabase database, String collectionName, Bson filter, FindOneAndDeleteOptions options) {
        return findOneAndDelete(getCollection(database, collectionName), filter, options);
    }

    public Document findOneAndDelete(MongoCollection<Document> collection, Bson filter, FindOneAndDeleteOptions options) {
        return collection.findOneAndDelete(filter, options);
    }

    public Document findOneAndReplace(String databaseName, String collectionName, Bson filter, Document replacement) {
        return findOneAndReplace(getDatabase(databaseName), collectionName, filter, replacement);
    }

    public Document findOneAndReplace(MongoDatabase database, String collectionName, Bson filter, Document replacement) {
        return findOneAndReplace(getCollection(database, collectionName), filter, replacement);
    }

    public Document findOneAndReplace(MongoCollection<Document> collection, Bson filter, Document replacement) {
        return collection.findOneAndReplace(filter, replacement);
    }

    public Document findOneAndReplace(String databaseName, String collectionName, Bson filter, Document replacement, FindOneAndReplaceOptions options) {
        return findOneAndReplace(getDatabase(databaseName), collectionName, filter, replacement, options);
    }

    public Document findOneAndReplace(MongoDatabase database, String collectionName, Bson filter, Document replacement, FindOneAndReplaceOptions options) {
        return findOneAndReplace(getCollection(database, collectionName), filter, replacement, options);
    }

    public Document findOneAndReplace(MongoCollection<Document> collection, Bson filter, Document replacement, FindOneAndReplaceOptions options) {
        return collection.findOneAndReplace(filter, replacement, options);
    }

    public Document findOneAndUpdate(String databaseName, String collectionName, Bson filter, Document replacement) {
        return findOneAndUpdate(getDatabase(databaseName), collectionName, filter, replacement);
    }

    public Document findOneAndUpdate(MongoDatabase database, String collectionName, Bson filter, Document replacement) {
        return findOneAndUpdate(getCollection(database, collectionName), filter, replacement);
    }

    public Document findOneAndUpdate(MongoCollection<Document> collection, Bson filter, Document replacement) {
        return collection.findOneAndUpdate(filter, replacement);
    }

    public Document findOneAndUpdate(String databaseName, String collectionName, Bson filter, Document replacement, FindOneAndUpdateOptions options) {
        return findOneAndUpdate(getDatabase(databaseName), collectionName, filter, replacement, options);
    }

    public Document findOneAndUpdate(MongoDatabase database, String collectionName, Bson filter, Document replacement, FindOneAndUpdateOptions options) {
        return findOneAndUpdate(getCollection(database, collectionName), filter, replacement, options);
    }

    public Document findOneAndUpdate(MongoCollection<Document> collection, Bson filter, Document replacement, FindOneAndUpdateOptions options) {
        return collection.findOneAndUpdate(filter, replacement, options);
    }

    public void drop(String databaseName, String collectionName) {
        drop(getDatabase(databaseName), collectionName);
    }

    public void drop(MongoDatabase database, String collectionName) {
        getCollection(database, collectionName).drop();
    }

    public void insertOne(String databaseName, String collectionName, Document document) {
        insertOne(getDatabase(databaseName), collectionName, document);
    }

    public void insertOne(MongoDatabase database, String collectionName, Document document) {
        getCollection(database, collectionName).insertOne(document);
    }

    public void insertMany(String databaseName, String collectionName, List<? extends Document> documents) {
        insertMany(getDatabase(databaseName), collectionName, documents);
    }

    public void insertMany(MongoDatabase database, String collectionName, List<? extends Document> documents) {
        getCollection(database, collectionName).insertMany(documents);
    }

    public void insertMany(String databaseName, String collectionName, List<? extends Document> documents, InsertManyOptions options) {
        insertMany(getDatabase(databaseName), collectionName, documents, options);
    }

    public void insertMany(MongoDatabase database, String collectionName, List<? extends Document> documents, InsertManyOptions options) {
        getCollection(database, collectionName).insertMany(documents, options);
    }

    public DeleteResult deleteOne(MongoDatabase database, String collectionName, Bson filter) {
        return getCollection(database, collectionName).deleteOne(filter);
    }

    public DeleteResult deleteOne(String databaseName, String collectionName, Bson filter) {
        return deleteOne(getDatabase(databaseName), collectionName, filter);
    }

    public DeleteResult deleteMany(MongoDatabase database, String collectionName, Bson filter) {
        return getCollection(database, collectionName).deleteMany(filter);
    }

    public DeleteResult deleteMany(String databaseName, String collectionName, Bson filter) {
        return deleteMany(getDatabase(databaseName), collectionName, filter);
    }

    public UpdateResult replaceOne(MongoDatabase database, String collectionName, Bson filter, Document replacement) {
        return getCollection(database, collectionName).replaceOne(filter, replacement);
    }

    public UpdateResult replaceOne(String databaseName, String collectionName, Bson filter, Document document) {
        return replaceOne(getDatabase(databaseName), collectionName, filter, document);
    }

    public UpdateResult replaceOne(MongoDatabase database, String collectionName, Bson filter, Document replacement, UpdateOptions updateOptions) {
        return getCollection(database, collectionName).replaceOne(filter, replacement, updateOptions);
    }

    public UpdateResult replaceOne(String databaseName, String collectionName, Bson filter, Document replacement, UpdateOptions updateOptions) {
        return replaceOne(getDatabase(databaseName), collectionName, filter, replacement, updateOptions);
    }

    public UpdateResult updateOne(MongoDatabase database, String collectionName, Bson filter, Document update) {
        return getCollection(database, collectionName).updateOne(filter, update);
    }

    public UpdateResult updateOne(String databaseName, String collectionName, Bson filter, Document update) {
        return updateOne(getDatabase(databaseName), collectionName, filter, update);
    }

    public UpdateResult updateOne(MongoDatabase database, String collectionName, Bson filter, Document update, UpdateOptions updateOptions) {
        return getCollection(database, collectionName).updateOne(filter, update, updateOptions);
    }

    public UpdateResult updateOne(String databaseName, String collectionName, Bson filter, Document update, UpdateOptions updateOptions) {
        return updateOne(getDatabase(databaseName), collectionName, filter, update, updateOptions);
    }

    public UpdateResult updateMany(MongoDatabase database, String collectionName, Bson filter, Document update) {
        return getCollection(database, collectionName).updateMany(filter, update);
    }

    public UpdateResult updateMany(String databaseName, String collectionName, Bson filter, Document update) {
        return updateMany(getDatabase(databaseName), collectionName, filter, update);
    }

    public UpdateResult updateMany(MongoDatabase database, String collectionName, Bson filter, Document update, UpdateOptions updateOptions) {
        return getCollection(database, collectionName).updateMany(filter, update, updateOptions);
    }

    public UpdateResult updateMany(String databaseName, String collectionName, Bson filter, Document update, UpdateOptions updateOptions) {
        return updateMany(getDatabase(databaseName), collectionName, filter, update, updateOptions);
    }

    public String createIndex(MongoDatabase database, String collectionName, Bson keys) {
        return getCollection(database, collectionName).createIndex(keys);
    }

    public String createIndex(String databaseName, String collectionName, Bson keys) {
        return createIndex(getDatabase(databaseName), collectionName, keys);
    }

    public String createIndex(MongoDatabase database, String collectionName, Bson keys, IndexOptions indexOptions) {
        return getCollection(database, collectionName).createIndex(keys, indexOptions);
    }

    public String createIndex(String databaseName, String collectionName, Bson keys, IndexOptions indexOptions) {
        return createIndex(getDatabase(databaseName), collectionName, keys, indexOptions);
    }

    public List<String> createIndexes(MongoDatabase database, String collectionName, List<IndexModel> indexes) {
        return getCollection(database, collectionName).createIndexes(indexes);
    }

    public List<String> createIndexes(String databaseName, String collectionName, List<IndexModel> indexes) {
        return createIndexes(getDatabase(databaseName), collectionName, indexes);
    }

    public ListIndexesIterable<Document> listIndexes(MongoDatabase database, String collectionName) {
        return getCollection(database, collectionName).listIndexes();
    }

    public ListIndexesIterable<Document> listIndexes(String databaseName, String collectionName) {
        return listIndexes(getDatabase(databaseName), collectionName);
    }

    public void dropIndex(MongoDatabase database, String collectionName, String indexName) {
        getCollection(database, collectionName).dropIndex(indexName);
    }

    public void dropIndex(String databaseName, String collectionName, String indexName) {
        dropIndex(getDatabase(databaseName), collectionName, indexName);
    }

    public void dropIndex(MongoDatabase database, String collectionName, Bson keys) {
        getCollection(database, collectionName).dropIndex(keys);
    }

    public void dropIndex(String databaseName, String collectionName, Bson keys) {
        dropIndex(getDatabase(databaseName), collectionName, keys);
    }

    public void dropIndexes(MongoDatabase database, String collectionName) {
        getCollection(database, collectionName).dropIndexes();
    }

    public void dropIndexes(String databaseName, String collectionName) {
        dropIndexes(getDatabase(databaseName), collectionName);
    }

    public void renameCollection(MongoDatabase database, String collectionName, MongoNamespace newCollectionNamespace) {
        getCollection(database, collectionName).renameCollection(newCollectionNamespace);
    }

    public void renameCollection(String databaseName, String collectionName, MongoNamespace newCollectionNamespace) {
        renameCollection(getDatabase(databaseName), collectionName, newCollectionNamespace);
    }

    public void renameCollection(MongoDatabase database, String collectionName, MongoNamespace newCollectionNamespace, RenameCollectionOptions renameCollectionOptions) {
        getCollection(database, collectionName).renameCollection(newCollectionNamespace, renameCollectionOptions);
    }

    public void renameCollection(String databaseName, String collectionName, MongoNamespace newCollectionNamespace, RenameCollectionOptions renameCollectionOptions) {
        renameCollection(getDatabase(databaseName), collectionName, newCollectionNamespace, renameCollectionOptions);
    }

    public MongoIterable<String> listDatabaseNames() {
        return mongo.listDatabaseNames();
    }

    public ListDatabasesIterable<Document> listDatabases() {
        return mongo.listDatabases();
    }

    public void runCommand(String databaseName, Bson command) {
        runCommand(getDatabase(databaseName), command);
    }

    public void runCommand(MongoDatabase database, Bson command) {
        database.runCommand(command);
    }

    public void runCommand(MongoDatabase database, Bson command, ReadPreference readPreference) {
        database.runCommand(command, readPreference);
    }

    public void runCommand(String databaseName, Bson command, ReadPreference readPreference) {
        runCommand(getDatabase(databaseName), command, readPreference);
    }

    public AggregateIterable<Document> aggregate(String databaseName, String collectionName, List<? extends Bson> pipeline) {
        return aggregate(getDatabase(databaseName), collectionName, pipeline);
    }

    public AggregateIterable<Document> aggregate(MongoDatabase database, String collectionName, List<? extends Bson> pipeline) {
        return aggregate(getCollection(database, collectionName), pipeline);
    }

    public AggregateIterable<Document> aggregate(MongoCollection<Document> mongoCollection, List<? extends Bson> pipeline) {
        return mongoCollection.aggregate(pipeline);
    }

    public MapReduceIterable<Document> mapReduce(MongoDatabase database, String collectionName, String mapFunction, String reduceFunction) {
        return mapReduce(getCollection(database, collectionName), mapFunction, reduceFunction);
    }

    public MapReduceIterable<Document> mapReduce(String databaseName, String collectionName, String mapFunction, String reduceFunction) {
        return mapReduce(getDatabase(databaseName), collectionName, mapFunction, reduceFunction);
    }

    @Override
    public MapReduceIterable<Document> mapReduce(MongoCollection<Document> mongoCollection, String mapFunction, String reduceFunction) {
        return mongoCollection.mapReduce(mapFunction, reduceFunction);
    }

    public BulkWriteResult bulkWrite(String databaseName, String collectionName, List<? extends WriteModel<? extends Document>> requests) {
        return bulkWrite(getDatabase(databaseName), collectionName, requests);
    }

    public BulkWriteResult bulkWrite(MongoDatabase database, String collectionName, List<? extends WriteModel<? extends Document>> requests) {
        return getCollection(database, collectionName).bulkWrite(requests);
    }

    public long count(MongoDatabase database, String collectionName) {
        return getCollection(database, collectionName).count();
    }

    public long count(String databaseName, String collectionName) {
        return count(getDatabase(databaseName), collectionName);
    }

    public long count(String databaseName, String collectionName, Bson filter) {
        return count(getDatabase(databaseName), collectionName, filter);
    }

    public long count(MongoDatabase database, String collectionName, Bson filter) {
        return getCollection(database, collectionName).count(filter);
    }

    @Override
    public long count(MongoCollection<Document> mongoCollection, Bson filter) {
        return mongoCollection.count(filter);
    }

    public long count(MongoDatabase database, String collectionName, Bson filter, CountOptions options) {
        return getCollection(database, collectionName).count(filter, options);
    }

    public long count(String databaseName, String collectionName, Bson filter, CountOptions options) {
        return count(getDatabase(databaseName), collectionName, filter, options);
    }

}
