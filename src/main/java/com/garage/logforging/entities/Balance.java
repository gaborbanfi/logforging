package com.garage.logforging.entities;

import java.math.BigDecimal;
import java.util.Currency;

public class Balance {

    private final BigDecimal amount;
    private final Currency currency;

    public Balance(BigDecimal amount, Currency currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public Currency getCurrency() {
        return currency;
    }
}
