package com.garage.logforging.controller;

import com.garage.logforging.service.AdminService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
public class AdminAPI {

    private static final Logger LOGGER = Logger.getLogger(AdminAPI.class);
    private final AdminService adminService;

    @Autowired
    public AdminAPI(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/fill")
    @ResponseBody
    public HttpStatus fillHistory() {
        try {
            adminService.fillHistory();
        } catch (Exception e) {
            LOGGER.error(e);
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.OK;
    }
}
