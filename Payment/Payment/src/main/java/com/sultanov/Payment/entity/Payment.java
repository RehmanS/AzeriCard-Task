package com.sultanov.Payment.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Entity
@Data
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    BigDecimal amount;
    LocalDateTime paymentDate;
    String username;
    @ElementCollection
    Map<Long, Integer> productAndQuantity;
}
