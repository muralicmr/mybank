package com.assignment.mybank.service;

import com.assignment.mybank.business.service.AccountService;
import com.assignment.mybank.business.service.MakePaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/")
@Slf4j
public class MyBankServices {

    private final AccountService accountService;
    private final MakePaymentService makePaymentService;

    public MyBankServices(final AccountService accountService, final MakePaymentService makePaymentService) {
        this.accountService = accountService;
        this.makePaymentService = makePaymentService;
    }

    @PostMapping(path = "/account", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity createAccount(final HttpEntity<String> httpEntity) throws IOException {

        String data = httpEntity.getBody();
        log.debug("Input request: [ {} ]", data);

        accountService.createAccount(data);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping(path = "/makePayment", consumes = "application/json", produces = "application/json")
    @ResponseBody
    public ResponseEntity makePayment(final HttpEntity<String> httpEntity) throws IOException {

        String data = httpEntity.getBody();
        log.debug("Input request: [ {} ]", data);

        makePaymentService.makePayment(data);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
