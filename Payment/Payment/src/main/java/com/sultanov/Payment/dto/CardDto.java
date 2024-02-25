package com.sultanov.Payment.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CardDto(
        String cardNumber,
        String cvv,
        BigDecimal balance,
        LocalDate expiredDate
) {
}
