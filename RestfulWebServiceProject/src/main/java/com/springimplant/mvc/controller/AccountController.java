package com.springimplant.mvc.controller;

import com.springimplant.mvc.model.Account;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

@RestController
@RequestMapping("/v1/forecasting")
public class AccountController {

    @RequestMapping(value = "accounts", method = RequestMethod.GET, produces = {MediaType.APPLICATION_JSON_VALUE})
    public Account[] getAccounts() {
        Account[] accounts = new Account[]{
                new Account("G435", "Gaurav", BigDecimal.valueOf(2300.60)),
                new Account("G789", "Mohit", BigDecimal.valueOf(4500.60))
        };
        return accounts;
    }
}
