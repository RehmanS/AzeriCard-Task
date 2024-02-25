package com.sultanov.Payment.dto;

import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Builder
public record PaymentResponse(
        BigDecimal amount,
        LocalDateTime paymentDate,
        String username,
        Map<Long, Integer> productAndQuantity
) {
}
