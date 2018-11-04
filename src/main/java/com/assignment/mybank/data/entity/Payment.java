package com.assignment.mybank.data.entity;

import lombok.Builder;
import lombok.Data;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name="PAYMENT")
@Data
@Builder
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name="ID")
    private long paymentId;

    @Column(name="FROM_ACCOUNT")
    private long fromAccount;

    @Column(name="TO_ACCOUNT")
    private long toAccount;

    @Column(name="AMOUNT")
    private BigDecimal amount;

    @Column(name="DESCRIPTION")
    private String description;

    
}
