package com.sultanov.Card.dto;

import java.math.BigDecimal;

public record CardCreateRequest(
        String username,
        BigDecimal balance,
        String expiredDate
) {
}
