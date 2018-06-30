package com.garage.logforging.repository;

import com.garage.logforging.entities.Balance;
import com.garage.logforging.entities.exception.NoBalanceFoundForTodayException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Currency;

@Repository
public class BankRepository {

    private final DBHelper dbHelper;

    @Autowired
    public BankRepository(DBHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public Balance getBalance(String userGuid) {
        MongoCollection<Document> collection = dbHelper.getHistoryCollection();

        Document searchQuery = new Document();
        searchQuery.put("user", userGuid);
        searchQuery.put("date", LocalDate.now().toEpochDay());

        FindIterable<Document> documents = collection.find(searchQuery);
        Document balance = documents.first();

        if (balance == null) {
            throw new NoBalanceFoundForTodayException();
        }

        BigDecimal amount = BigDecimal.valueOf((double) balance.get("amount"));
        Currency currency = Currency.getInstance((String) balance.get("currency"));
        return new Balance(amount, currency);
    }
}
