package com.garage.logforging.repository;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.util.IdGenerator;
import org.springframework.util.SimpleIdGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Currency;
import java.util.List;

@Repository
public class AdminRepository {

    private final DBHelper dbHelper;
    private final IdGenerator guidGenerator;

    @Autowired
    public AdminRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
        this.guidGenerator = new SimpleIdGenerator();
    }

    public void fillHistory() {
        MongoDatabase bankingDB = dbHelper.getDatabase();
        bankingDB.createCollection(dbHelper.getHistoryCollectionName());

        MongoCollection<Document> collection = dbHelper.getHistoryCollection(bankingDB);
        List<Document> history = new ArrayList<>();
        int userCount = (int)(Math.random() * 10);
        for (int i = 0; i <= userCount; i++) {
            String guid = guidGenerator.generateId().toString();
            int historyCount = (int)(Math.random() * 50);
            LocalDate now = LocalDate.now();
            for (int j = 0; j <= historyCount; j++) {
                Document document = new Document();
                document.put("user", guid);
                document.put("amount", Math.random() * 200 + 50);
                document.put("currency", Currency.getInstance("USD").toString());
                document.put("date", now.toEpochDay());
                history.add(document);

                now = now.minusDays(1L);
            }
        }
        history.sort(Comparator.comparing(x -> ((Long) x.get("date"))));

        collection.insertMany(history);
    }

    public void dropHistory() {
        MongoCollection<Document> history = dbHelper.getHistoryCollection();

        history.drop();
    }
}
