package com.assignment.mybank.validator;

import com.assignment.mybank.business.domain.CreateAccountRequest;
import com.assignment.mybank.business.domain.MakePaymentRequest;
import com.assignment.mybank.business.domain.MyBankProblemDetails;
import com.assignment.mybank.data.entity.Account;
import com.assignment.mybank.data.repository.AccountRepository;
import com.assignment.mybank.exception.MyBankException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

@Component
@Slf4j
public class MakePaymentValidator {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public boolean validate(final MakePaymentRequest request, final Account fromAcc, final Account toAcc) {

        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<MakePaymentRequest>> violations = validator.validate(request);

        log.debug("violations: {}", violations);

        if (violations.size() > 0) {
            log.error("violation: {}", violations.toString());
            throw new MyBankException(MyBankProblemDetails.INVALID_REQUEST);
        }

        if (fromAcc == null) {
            throw new MyBankException(MyBankProblemDetails.INVALID_FROM_ACCOUNT);
        } else if (toAcc == null) {
            throw new MyBankException(MyBankProblemDetails.INVALID_TO_ACCOUNT);
        } else if (request.getFromAccount() == request.getToAccount()) {
            throw new MyBankException(MyBankProblemDetails.SAME_FROM_ACC_AND_TO_ACC);
        }

        return true;
    }

}
