package com.bookstore.securityservice.requests;

import com.bookstore.securityservice.responses.CardResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.List;

@Builder
public record PaymentRequest(
        String username,
        CardResponse card,
        BigDecimal totalPrice,
        List<Item> items
) {
}
