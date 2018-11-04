package com.assignment.mybank.business.service;

import com.assignment.mybank.business.domain.MyBankProblemDetails;
import com.assignment.mybank.data.entity.Account;
import com.assignment.mybank.data.repository.AccountRepository;
import com.assignment.mybank.exception.MyBankException;
import com.assignment.mybank.validator.CreateAccountValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;

import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private CreateAccountValidator createAccountValidator;

    private AccountService accountService;

    @Mock
    private Account account;

    private final String ACC_INPUT = "{\"firstName\":\"Test\",\"lastName\":\"User\",\"balance\":\"1343.21\"}";

    @Before
    public void setup() {
        accountService = new AccountService(objectMapper, accountRepository, createAccountValidator);
    }

    @Test
    public void test_createAccount_success() throws IOException {
        when(createAccountValidator.validate(any())).thenReturn(true);
        when(accountRepository.save(any())).thenReturn(account);

        final Account account = accountService.createAccount(ACC_INPUT);

        assertNotNull(account);
    }

    @Test (expected = MyBankException.class)
    public void test_createAccount_validationError() throws IOException {
        doThrow(new MyBankException(MyBankProblemDetails.INVALID_REQUEST)).when(createAccountValidator).validate(any());

        accountService.createAccount(ACC_INPUT);
    }

    @Test (expected = Exception.class)
    public void test_createAccount_saveError() throws IOException {
        when(createAccountValidator.validate(any())).thenReturn(true);
        doThrow(new RuntimeException()).when(accountRepository).save(any());

        accountService.createAccount(ACC_INPUT);
    }

}
