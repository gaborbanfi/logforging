package com.garage.logforging.repository;

import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.IdGenerator;
import org.springframework.util.SimpleIdGenerator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Currency;
import java.util.List;

@Repository
public class AdminRepository {

    private final IdGenerator guidGenerator;

    @Autowired
    public AdminRepository() {
        this.guidGenerator = new SimpleIdGenerator();
    }

    public void fillHistory() {
        MongoClient mongoClient = new MongoClient("localhost", 27017);
        MongoDatabase bankingDB = mongoClient.getDatabase("bankingDB");
        bankingDB.createCollection("history");

        MongoCollection<Document> collection = bankingDB.getCollection("history");
        List<Document> history = new ArrayList<>();
        int userCount = (int)(Math.random() * 10);
        for (int i = 0; i <= userCount; i++) {
            String guid = guidGenerator.generateId().toString();
            int historyCount = (int)(Math.random() * 50);
            for (int j = 0; j <= historyCount; j++) {
                Document document = new Document();
                document.put("user", guid);
                document.put("amount", Math.random() * 200 + 50);
                document.put("currency", Currency.getInstance("USD").toString());
                history.add(document);
            }
        }

        Collections.shuffle(history);
        collection.insertMany(history);
    }
}
