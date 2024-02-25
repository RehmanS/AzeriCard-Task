package com.sultanov.Card.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    String cardNumber;
    String cvv;
    BigDecimal balance;
    LocalDate createdDate;
    LocalDate expiredDate;
    Byte state;
    String username;
}
