package com.assignment.mybank.business.service;

import com.assignment.mybank.business.domain.MakePaymentRequest;
import com.assignment.mybank.business.domain.MyBankProblemDetails;
import com.assignment.mybank.data.entity.Account;
import com.assignment.mybank.data.entity.Payment;
import com.assignment.mybank.data.repository.AccountRepository;
import com.assignment.mybank.data.repository.PaymentRepository;
import com.assignment.mybank.exception.MyBankException;
import com.assignment.mybank.validator.MakePaymentValidator;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.io.IOException;
import java.math.BigDecimal;

@Service
@Slf4j
public class MakePaymentService {

    private final ObjectMapper objectMapper;
    private final PaymentRepository paymentRepository;
    private final AccountRepository accountRepository;
    private final MakePaymentValidator makePaymentValidator;

    public MakePaymentService(final ObjectMapper objectMapper,
                              final PaymentRepository paymentRepository,
                              final AccountRepository accountRepository,
                              final MakePaymentValidator makePaymentValidator) {
        this.objectMapper = objectMapper;
        this.paymentRepository = paymentRepository;
        this.accountRepository = accountRepository;
        this.makePaymentValidator = makePaymentValidator;
    }

    @Transactional
    public void makePayment(final String data) throws IOException {
        final MakePaymentRequest makePaymentRequest = objectMapper.readValue(data, MakePaymentRequest.class);

        log.debug("makePaymentRequest {}", makePaymentRequest);

        // retrieve account info for from and to account
        final Account fromAcc = accountRepository.findByAccountId(makePaymentRequest.getFromAccount());
        final Account toAcc = accountRepository.findByAccountId(makePaymentRequest.getToAccount());

        log.debug("fromAcc: " + fromAcc);
        log.debug("toAcc: " + toAcc);

        makePaymentValidator.validate(makePaymentRequest, fromAcc, toAcc);

        // save to makePayment
        paymentRepository.save(transform(makePaymentRequest));

        // update accounts
        final BigDecimal paymentValue = BigDecimal.valueOf(makePaymentRequest.getAmount());
        BigDecimal fromAccountBalance = fromAcc.getBalance().subtract(paymentValue);

        if (fromAccountBalance.signum() < 0) {
            throw new MyBankException(MyBankProblemDetails.INSUFFICIENT_BALANCE);
        }

        fromAcc.setBalance(fromAccountBalance);

        BigDecimal toAccountBalance = toAcc.getBalance().add(paymentValue);
        toAcc.setBalance(toAccountBalance);

        // save acc details
        accountRepository.save(fromAcc);
        accountRepository.save(toAcc);
    }

    private Payment transform(MakePaymentRequest makePaymentRequest) {
        return Payment.builder()
                .fromAccount(makePaymentRequest.getFromAccount())
                .toAccount(makePaymentRequest.getToAccount())
                .amount(BigDecimal.valueOf(makePaymentRequest.getAmount()))
                .description(makePaymentRequest.getDescription())
                .build();
    }

}
