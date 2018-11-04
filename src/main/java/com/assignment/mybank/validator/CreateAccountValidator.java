package com.assignment.mybank.validator;

import com.assignment.mybank.business.domain.CreateAccountRequest;
import com.assignment.mybank.business.domain.MyBankProblemDetails;
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
public class CreateAccountValidator {

    private final ValidatorFactory factory = Validation.buildDefaultValidatorFactory();

    public boolean validate(CreateAccountRequest request) {

        final Validator validator = factory.getValidator();
        Set<ConstraintViolation<CreateAccountRequest>> violations = validator.validate(request);

        log.debug("violations: {}", violations);

        if (violations.size() > 0) {
            log.error("violation: {}", violations.toString());
            throw new MyBankException(MyBankProblemDetails.INVALID_REQUEST);
        }

        return true;
    }
}
