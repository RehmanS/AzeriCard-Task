package com.sultanov.Product.dto;

import java.math.BigDecimal;

public record ProductCreateRequest(
        String name,
        Long stockCount,
        BigDecimal price
) {
}
