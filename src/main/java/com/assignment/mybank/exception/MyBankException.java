package com.assignment.mybank.exception;

import com.assignment.mybank.business.domain.MyBankProblemDetails;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;

@Getter
@Setter
public class MyBankException extends RuntimeException {

    private HttpStatus httpStatus;
    private MyBankProblemDetails myBankProblemDetails;

    public MyBankException(final Throwable throwable, final HttpStatus statusCode, final MyBankProblemDetails myBankProblemDetails) {
        super(throwable);
        this.httpStatus = statusCode;
        this.myBankProblemDetails = myBankProblemDetails;
    }

    public MyBankException(final Throwable throwable, final MyBankProblemDetails myBankProblemDetails) {
        super(throwable);
        this.myBankProblemDetails = myBankProblemDetails;
    }

    public MyBankException(final MyBankProblemDetails myBankProblemDetails) {
        this.myBankProblemDetails = myBankProblemDetails;
    }

}
