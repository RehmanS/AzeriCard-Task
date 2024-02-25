package com.sultanov.Card.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
public record CardResponse(
        String cardNumber,
        String cvv,
        BigDecimal balance,
        LocalDate expiredDate
) {
}
