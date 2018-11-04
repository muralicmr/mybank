package com.assignment.mybank.business.service;

import com.assignment.mybank.business.domain.CreateAccountRequest;
import com.assignment.mybank.data.entity.Account;
import com.assignment.mybank.data.repository.AccountRepository;
import com.assignment.mybank.validator.CreateAccountValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;

@Service
@Slf4j
public class AccountService {

    private final ObjectMapper objectMapper;
    private final AccountRepository accountRepository;
    private final CreateAccountValidator createAccountValidator;

    public AccountService(final ObjectMapper objectMapper, final AccountRepository accountRepository, final CreateAccountValidator createAccountValidator) {
        this.objectMapper = objectMapper;
        this.accountRepository = accountRepository;
        this.createAccountValidator = createAccountValidator;
    }

    public Account createAccount(final String data) throws IOException {
        final CreateAccountRequest createAccountRequest = objectMapper.readValue(data, CreateAccountRequest.class);
        log.debug("createAccountRequest: {}", createAccountRequest);

        createAccountValidator.validate(createAccountRequest);

        return accountRepository.save(transform(createAccountRequest));
    }

    private Account transform(CreateAccountRequest createAccountRequest) {
        return Account.builder()
                    .firstName(createAccountRequest.getFirstName())
                    .lastName(createAccountRequest.getLastName())
                    .balance(BigDecimal.valueOf(createAccountRequest.getBalance()))
                    .build();
    }
}
