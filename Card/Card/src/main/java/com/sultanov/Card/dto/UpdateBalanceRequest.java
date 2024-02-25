package com.sultanov.Card.dto;

import java.math.BigDecimal;

public record UpdateBalanceRequest(
        String cardNumber,
        BigDecimal amount
) {
}
