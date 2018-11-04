package com.assignment.mybank.business.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;

@Data
@Builder
public class MakePaymentRequest {

    @DecimalMin(value = "1", message = "valid fromAccount needs to be present")
    private long fromAccount;

    @DecimalMin(value = "1", message = "valid toAccount needs to be present")
    private long toAccount;

    @DecimalMin(value = ".1", message = "valid amount needs to be present")
    private double amount;

    private String description;

}
