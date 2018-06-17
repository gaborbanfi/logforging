package com.garage.logforging.service;

import com.garage.logforging.entities.Balance;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import static org.owasp.esapi.ESAPI.encoder;

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

    public Balance getSecureBalanceOfUser(String userGuid) {
        LOGGER.info("Balance is requested for user id = " + encode(userGuid));
        return bankRepository.getBalance(userGuid);
    }

    private String encode(String message) {
        message = message.replace('\n', '_')
                        .replace('\r', '_')
                        .replace('\t', '_');
        message = encoder().encodeForHTML(message);
        return message;
    }
}
