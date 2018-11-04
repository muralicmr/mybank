package com.assignment.mybank.validator;

import com.assignment.mybank.business.domain.CreateAccountRequest;
import com.assignment.mybank.exception.MyBankException;
import org.junit.Test;

import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertEquals;

public class CreateAccountValidatorTest {

    private CreateAccountValidator createAccountValidator = new CreateAccountValidator();

    @Test
    public void test_validate_valid() {

        CreateAccountRequest accountRequest = CreateAccountRequest.builder()
                .firstName("fn")
                .lastName("ln")
                .balance(123)
                .build();

        final boolean valid = createAccountValidator.validate(accountRequest);

        assertTrue(valid);
    }

    @Test
    public void test_validate_valid_withZeroBalance() {

        CreateAccountRequest accountRequest = CreateAccountRequest.builder()
                .firstName("fn")
                .lastName("ln")
                .balance(0)
                .build();

        final boolean valid = createAccountValidator.validate(accountRequest);

        assertTrue(valid);
    }

    @Test
    public void test_validate_fnMissing() {

        CreateAccountRequest accountRequest = CreateAccountRequest.builder()
                .lastName("ln")
                .balance(123)
                .build();

        try{
            createAccountValidator.validate(accountRequest);
        } catch (MyBankException e) {
            assertEquals("Request invalid", e.getMyBankProblemDetails().getTitle());
        }
    }

    @Test
    public void test_validate_lnMissing() {

        CreateAccountRequest accountRequest = CreateAccountRequest.builder()
                .firstName("fn")
                .balance(123)
                .build();

        try{
            createAccountValidator.validate(accountRequest);
        } catch (MyBankException e) {
            assertEquals("Request invalid", e.getMyBankProblemDetails().getTitle());
        }
    }

    @Test
    public void test_validate_negativeBalance() {

        CreateAccountRequest accountRequest = CreateAccountRequest.builder()
                .firstName("fn")
                .lastName("ln")
                .balance(-123)
                .build();

        try{
            createAccountValidator.validate(accountRequest);
        } catch (MyBankException e) {
            assertEquals("Request invalid", e.getMyBankProblemDetails().getTitle());
        }
    }
}
