package com.bookstore.securityservice.requests;

public record Item(
        Long productId,
        Integer quantity
) {
}
