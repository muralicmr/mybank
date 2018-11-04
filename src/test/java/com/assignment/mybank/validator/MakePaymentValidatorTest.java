package com.assignment.mybank.validator;

import com.assignment.mybank.business.domain.MakePaymentRequest;
import com.assignment.mybank.data.entity.Account;
import com.assignment.mybank.exception.MyBankException;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class MakePaymentValidatorTest {

    private MakePaymentValidator makePaymentValidator = new MakePaymentValidator();

    @Test
    public void test_validate_valid() {

        Account acc = Account.builder().build();

        MakePaymentRequest makePaymentRequest = MakePaymentRequest.builder()
                .fromAccount(1)
                .toAccount(2)
                .amount(100.00)
                .description("test")
                .build();

        final boolean valid = makePaymentValidator.validate(makePaymentRequest, acc, acc);

        assertTrue(valid);
    }

    @Test
    public void test_validate_valid_withZeroBalance() {

        Account acc = Account.builder().build();

        MakePaymentRequest makePaymentRequest = MakePaymentRequest.builder()
                .fromAccount(1)
                .toAccount(2)
                .amount(00.00)
                .description("test")
                .build();

        try {
            makePaymentValidator.validate(makePaymentRequest, acc, acc);
        } catch (MyBankException e) {
            assertEquals("Request invalid", e.getMyBankProblemDetails().getTitle());
        }
    }

    @Test
    public void test_validate_fromAccount_Missing() {

        Account acc = Account.builder().build();

        MakePaymentRequest makePaymentRequest = MakePaymentRequest.builder()
                .toAccount(2)
                .amount(00.00)
                .description("test")
                .build();

        try {
            makePaymentValidator.validate(makePaymentRequest, acc, acc);
        } catch (MyBankException e) {
            assertEquals("Request invalid", e.getMyBankProblemDetails().getTitle());
        }
    }

    @Test
    public void test_validate_toAccount_Missing() {

        Account acc = Account.builder().build();

        MakePaymentRequest makePaymentRequest = MakePaymentRequest.builder()
                .fromAccount(1)
                .amount(00.00)
                .description("test")
                .build();

        try {
            makePaymentValidator.validate(makePaymentRequest, acc, acc);
        } catch (MyBankException e) {
            assertEquals("Request invalid", e.getMyBankProblemDetails().getTitle());
        }
    }

    @Test
    public void test_validate_negativeBalance() {

        Account acc = Account.builder().build();

        MakePaymentRequest makePaymentRequest = MakePaymentRequest.builder()
                .fromAccount(1)
                .toAccount(2)
                .amount(-100.00)
                .description("test")
                .build();

        try {
            makePaymentValidator.validate(makePaymentRequest, acc, acc);
        } catch (MyBankException e) {
            assertEquals("Request invalid", e.getMyBankProblemDetails().getTitle());
        }
    }

    @Test
    public void test_validate_missingFromAcc() {

        Account acc = Account.builder().build();

        MakePaymentRequest makePaymentRequest = MakePaymentRequest.builder()
                .fromAccount(1)
                .toAccount(2)
                .amount(100.00)
                .description("test")
                .build();

        try {
            makePaymentValidator.validate(makePaymentRequest, null, acc);
        } catch (MyBankException e) {
            assertEquals("invalid from account", e.getMyBankProblemDetails().getTitle());
        }
    }

    @Test
    public void test_validate_missingToAcc() {

        Account acc = Account.builder().build();

        MakePaymentRequest makePaymentRequest = MakePaymentRequest.builder()
                .fromAccount(1)
                .toAccount(2)
                .amount(100.00)
                .description("test")
                .build();

        try {
            makePaymentValidator.validate(makePaymentRequest, acc, null);
        } catch (MyBankException e) {
            assertEquals("invalid to account", e.getMyBankProblemDetails().getTitle());
        }
    }

    @Test
    public void test_validate_sameFromAndToAccount() {
        Account acc = Account.builder().build();

        MakePaymentRequest makePaymentRequest = MakePaymentRequest.builder()
                .fromAccount(1)
                .toAccount(1)
                .amount(100.00)
                .description("test")
                .build();

        try {
            makePaymentValidator.validate(makePaymentRequest, acc, acc);
        } catch (MyBankException e) {
            assertEquals("same from and to account", e.getMyBankProblemDetails().getTitle());
        }
    }
}
