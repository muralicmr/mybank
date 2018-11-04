package com.assignment.mybank.business.domain;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;

@Data
@Builder
public class CreateAccountRequest {

    @NotEmpty(message = "firstName cannot be null")
    private String firstName;

    @NotEmpty(message = "lastName cannot be null")
    private String lastName;

    @DecimalMin(value = "0", message = "balance should be atleast 0")
    private double balance;

}
