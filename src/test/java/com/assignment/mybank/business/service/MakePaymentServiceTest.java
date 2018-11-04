package com.assignment.mybank.business.service;

import com.assignment.mybank.data.entity.Account;
import com.assignment.mybank.data.repository.AccountRepository;
import com.assignment.mybank.data.repository.PaymentRepository;
import com.assignment.mybank.exception.MyBankException;
import com.assignment.mybank.validator.MakePaymentValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.IOException;
import java.math.BigDecimal;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class MakePaymentServiceTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private AccountRepository accountRepository;

    @Mock
    private MakePaymentValidator makePaymentValidator;

    private MakePaymentService makePaymentService;

    private Account fromAccount;

    private Account toAccount;

    private final String MAKE_PAYMENT_INPUT = "{\"fromAccount\":1,\"toAccount\":2,\"amount\":\"1000\",\"description\":\"Test transfer\"}";

    @Before
    public void setup() {
        System.out.println(paymentRepository);
        System.out.println(accountRepository);
        makePaymentService = new MakePaymentService(objectMapper, paymentRepository, accountRepository, makePaymentValidator);
    }

    @Test
    public void test_makePayment_success() throws IOException {
        fromAccount = Account.builder().accountId(1).balance(BigDecimal.valueOf(1200)).firstName("fn1").lastName("ln1").build();
        toAccount = Account.builder().accountId(2).balance(BigDecimal.valueOf(100)).firstName("fn2").lastName("ln2").build();
        when(accountRepository.findByAccountId(anyLong())).thenReturn(fromAccount).thenReturn(toAccount);
        when(makePaymentValidator.validate(any(), any(), any())).thenReturn(true);

        makePaymentService.makePayment(MAKE_PAYMENT_INPUT);
    }

    @Test
    public void test_makePayment_insufficientBalance() throws IOException {
        fromAccount = Account.builder().accountId(1).balance(BigDecimal.valueOf(10)).firstName("fn1").lastName("ln1").build();
        toAccount = Account.builder().accountId(2).balance(BigDecimal.valueOf(10)).firstName("fn2").lastName("ln2").build();
        when(accountRepository.findByAccountId(anyLong())).thenReturn(fromAccount).thenReturn(toAccount);
        when(makePaymentValidator.validate(any(), any(), any())).thenReturn(true);

        try {
            makePaymentService.makePayment(MAKE_PAYMENT_INPUT);
        } catch (MyBankException e) {
            assertEquals("insufficient balance", e.getMyBankProblemDetails().getTitle());
        }
    }

    // TODO: More test cases

}
