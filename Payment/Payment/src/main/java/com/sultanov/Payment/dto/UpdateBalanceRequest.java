package com.sultanov.Payment.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record UpdateBalanceRequest(
        String cardNumber,
        BigDecimal amount
) {
}
