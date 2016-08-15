package com.ls.test.mongo;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.junit.Test;

/**
 * Created by hx on 16-7-7.
 */
public class MongoTest {

    @Test
    public void testInsertObjectAndGet() {
//        System.out.println(
//                Filters_bak.and(
//                        Filters_bak.eq(EntityConst.FieldName.AccountFN.NAME, "ceshi"),
//                        Filters_bak.elemMatch(
//                                EntityConst.FieldName.AccountFN.APPLICATIONS,
//                                Filters_bak.eq(EntityConst.FieldName.ApplicationFN.NAME, "test_app"))
//                ).toBsonDocument(Document.class, MongoClient.getDefaultCodecRegistry()).toJson()
//        );


        MongoClient client = new MongoClient();
        MongoDatabase db = client.getDatabase("temp");
        MongoCollection<Document> collection = db.getCollection("temp");

//        Account account = new Account();
//        account.setName("ceshi");
//        account.setId("abc");
//
//        Application application = new Application();
//        application.setName("test_app");
//
//        VirtualSensor sensor = new VirtualSensor();
//        sensor.setName("v1");
//        sensor.setId("v1");
//
//        application.addSensors(sensor);
//
//        account.addApplications(application);
//
//        collection.drop();
//        collection.insertOne(account.toDocument());

//        BsonDocument filterBson = Filters_bak.elemMatch(
//                EntityConst.FieldName.AccountFN.APPLICATIONS,
//                Filters_bak.elemMatch(
//                        EntityConst.FieldName.ApplicationFN.SENSORS,
//                        Filters_bak.eq(EntityConst.FieldName.VirtualSensorFN.NAME, "v1"))
//        ).toBsonDocument(BsonDocument.class, db.getCodecRegistry());

        Document filterDoc = Document.parse("{ \"account_applications\" : { \"$elemMatch\" : { \"app_sensors\" : { \"$elemMatch\" : { \"vs_name\" : \"v1\" } } } } }");

        FindIterable<Document> findIterable = collection.find(filterDoc);
        MongoCursor<Document> mongoCursor = findIterable.iterator();
        while (mongoCursor.hasNext()) {
            Document document = mongoCursor.next();
            System.out.println(document);
        }
    }

}
