package com.garage.logforging.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class DBHelper {

    MongoDatabase getDatabase() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        return mongoClient.getDatabase("bankingDB");
    }

    MongoCollection<Document> getHistoryCollection() {
        MongoDatabase bankingDB = getDatabase();
        return bankingDB.getCollection(getHistoryCollectionName());
    }

    MongoCollection<Document> getHistoryCollection(MongoDatabase bankingDB) {
        return bankingDB.getCollection(getHistoryCollectionName());
    }

    String getHistoryCollectionName() {
        return "history";
    }
}
