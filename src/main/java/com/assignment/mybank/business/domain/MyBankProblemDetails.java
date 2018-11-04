package com.assignment.mybank.business.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.net.URI;
import java.util.Objects;

@Data
public class MyBankProblemDetails {

    public static final MyBankProblemDetails INVALID_REQUEST =
            new MyBankProblemDetails(URI.create("/errors/invalid-request"), "Request invalid", HttpStatus.BAD_REQUEST);

    public static final MyBankProblemDetails INVALID_FROM_ACCOUNT =
            new MyBankProblemDetails(URI.create("/errors/invalid-from-account"), "invalid from account", HttpStatus.BAD_REQUEST);

    public static final MyBankProblemDetails INVALID_TO_ACCOUNT =
            new MyBankProblemDetails(URI.create("/errors/invalid-to-account"), "invalid to account", HttpStatus.BAD_REQUEST);

    public static final MyBankProblemDetails SAME_FROM_ACC_AND_TO_ACC =
            new MyBankProblemDetails(URI.create("/errors/from-account-and-to-account-same"), "same from and to account", HttpStatus.BAD_REQUEST);

    public static final MyBankProblemDetails INSUFFICIENT_BALANCE =
            new MyBankProblemDetails(URI.create("/errors/insuficient-balance"), "insufficient balance", HttpStatus.BAD_REQUEST);

    public static final MyBankProblemDetails SERVICE_UNAVAILABLE =
            new MyBankProblemDetails(URI.create("/errors/service-unavailable"), "Service unavailable.", HttpStatus.SERVICE_UNAVAILABLE);

    private final URI type;
    private final String title;
    private final HttpStatus status;

    /**
     * Public constructor. Used by Jackson for deserializing.
     *
     * @param type   A (non-dereferencable) relative URI identifying the problem type.
     * @param title  A human-readable summary of the problem type.
     * @param status The HTTP status code.
     */
    @JsonCreator
    public MyBankProblemDetails(
            @JsonProperty("type") final URI type,
            @JsonProperty("title") final String title,
            @JsonProperty("status") final HttpStatus status) {
        this.type = Objects.requireNonNull(type);
        this.title = Objects.requireNonNull(title);
        this.status = status;
    }
}
