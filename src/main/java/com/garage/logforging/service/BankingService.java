package com.garage.logforging.service;

import com.garage.logforging.entities.Balance;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BankingService {

    private static final Logger LOGGER = Logger.getLogger(BankingService.class);

    private final BankRepository bankRepository;

    @Autowired
    public BankingService(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    public Balance getBalanceOfUser(String userGuid) {
        LOGGER.info("Balance is requested for user id = " + userGuid);
        return bankRepository.getBalance(userGuid);
    }
}
