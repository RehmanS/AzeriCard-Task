package com.sultanov.Product.dto;

import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record ProductResponse(
        String name,
        Long stockCount,
        BigDecimal price
) {
}
