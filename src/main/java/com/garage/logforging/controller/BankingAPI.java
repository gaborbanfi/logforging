package com.garage.logforging.controller;

import com.garage.logforging.entities.Balance;
import com.garage.logforging.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BankingAPI {

    private final BankingService bankingService;

    @Autowired
    public BankingAPI(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    @PostMapping("/balance")
    @ResponseBody
    public Balance logforgingAllowingEndpoint(@RequestBody String guid) {
        return bankingService.getBalanceOfUser(guid);
        // Request Body
        // 16\r\n2018-06-17 16:32:54.494  INFO 6416 --- [nio-8080-exec-4] c.g.logforging.service.BankingService    :  java.lang.NullPointerException
    }
}
