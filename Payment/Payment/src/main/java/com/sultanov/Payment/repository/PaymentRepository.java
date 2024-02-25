package com.sultanov.Payment.repository;

import com.sultanov.Payment.entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
    List<Payment> findPaymentsByUsername(String username);
}
