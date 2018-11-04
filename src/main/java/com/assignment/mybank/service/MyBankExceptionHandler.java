package com.assignment.mybank.service;

import com.assignment.mybank.business.domain.MyBankProblemDetails;
import com.assignment.mybank.exception.MyBankException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
@Slf4j
public class MyBankExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(MyBankException.class)
    public ResponseEntity<MyBankProblemDetails> myBankExceptionHandler(final MyBankException ex) {
        log.warn("Constraint violation exception occurred: {}", ex.getMyBankProblemDetails());
        log.debug("Stacktrace", ex);
        if (ex.getHttpStatus() == null) {
            ex.setHttpStatus(ex.getMyBankProblemDetails().getStatus());
        }
        return ResponseEntity.status(ex.getHttpStatus()).body(ex.getMyBankProblemDetails());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<MyBankProblemDetails> run(final RuntimeException ex) {
        log.debug("Stacktrace", ex);
        return ResponseEntity.status(HttpStatus.SERVICE_UNAVAILABLE).body(MyBankProblemDetails.SERVICE_UNAVAILABLE);
    }
}
