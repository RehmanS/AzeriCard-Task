package com.bookstore.securityservice.requests;

import java.util.List;

public record OrderRequest(
        String cardNumber,
        String cvv,
        List<Item> items
) {
}
