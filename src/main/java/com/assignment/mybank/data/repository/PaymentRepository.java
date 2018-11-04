package com.assignment.mybank.data.repository;

import com.assignment.mybank.data.entity.Account;
import com.assignment.mybank.data.entity.Payment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends CrudRepository<Payment, Long> {

    Payment findByPaymentId(String id);

}
