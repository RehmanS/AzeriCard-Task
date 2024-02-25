package com.sultanov.Payment.dto;

import java.math.BigDecimal;
import java.util.List;

public record PaymentRequest(
        String username,
        CardDto card,
        BigDecimal totalPrice,
        List<Item> items
) {
}
