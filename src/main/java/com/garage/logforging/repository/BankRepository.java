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
import java.util.ArrayList;
import java.util.Currency;
import java.util.List;
import java.util.function.Consumer;

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

    public List<Balance> getHistoryForUser(String guid) {
        MongoCollection<Document> collection = dbHelper.getHistoryCollection();

        Document searchQuery = new Document();
        searchQuery.put("user", guid);

        FindIterable<Document> documents = collection.find(searchQuery);
        List<Balance> result = new ArrayList<>();

        documents.forEach((Consumer<? super Document>) document -> {
            BigDecimal amount = BigDecimal.valueOf((double) document.get("amount"));
            Currency currency = Currency.getInstance((String) document.get("currency"));
            result.add(new Balance(amount, currency));
        });

        return result;
    }
}
