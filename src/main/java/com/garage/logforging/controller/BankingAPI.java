package com.garage.logforging.controller;

import com.garage.logforging.entities.Balance;
import com.garage.logforging.service.BankingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.util.List;

@RestController
public class BankingAPI {

    private final BankingService bankingService;

    @Autowired
    public BankingAPI(BankingService bankingService) {
        this.bankingService = bankingService;
    }

    // for testing:
    // Request Body
    // 16\r\n2018-06-17 16:32:54.494  INFO 6416 --- [nio-8080-exec-4] c.g.logforging.service.BankingService    :  java.lang.NullPointerException

    @PostMapping("/balance")
    @ResponseBody
    public Balance logforgingAllowingEndpoint(@RequestBody String guid) throws Exception {
        memoryLeak();
        return bankingService.getBalanceOfUser(guid);
        // logged message
        // 22613 [http-nio-8080-exec-2] INFO  com.garage.logforging.service.BankingService  - Balance is requested for user id = 16\r\n2018-06-17 16:32:54.494  INFO 6416 --- [nio-8080-exec-4] c.g.logforging.service.BankingService    :  java.lang.NullPointerException
    }

    private void memoryLeak() throws Exception {
        Class unsafeClass = Class.forName("sun.misc.Unsafe");
        Field f = unsafeClass.getDeclaredField("theUnsafe");
        f.setAccessible(true);
        Unsafe unsafe = (Unsafe) f.get(null);
        try {
            unsafe.allocateMemory(100 * 1024 * 1024); // 100 MB memory
        } catch (Error e) {
            e.printStackTrace();
        }
    }

    @PostMapping("/secure/balance")
    @ResponseBody
    public Balance logforgingSecureEndpoint(@RequestBody String guid) {
        return bankingService.getSecureBalanceOfUser(guid);
        // logged message
        // 0    [http-nio-8080-exec-1] INFO  com.garage.logforging.service.BankingService  - Balance is requested for user id = 16&#x5c;r&#x5c;n2018-06-17 16&#x3a;32&#x3a;54.494  INFO 6416 --- &#x5b;nio-8080-exec-4&#x5d; c.g.logforging.service.BankingService    &#x3a;  java.lang.NullPointerException
    }

    @GetMapping("/history/{guid}")
    @ResponseBody
    public List<Balance> getHistoryForUser(@PathVariable String guid) {
        return bankingService.getHistoryForUser(guid);
    }
}
