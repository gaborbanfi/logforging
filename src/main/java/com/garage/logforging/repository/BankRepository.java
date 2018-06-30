package com.garage.logforging.repository;

import com.garage.logforging.entities.Balance;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Currency;

@Repository
public class BankRepository {

    public Balance getBalance(String userGuid) {
        BigDecimal amount = BigDecimal.valueOf(2048L);
        Currency currency = Currency.getInstance("USD");
        return new Balance(amount, currency);
    }
}
