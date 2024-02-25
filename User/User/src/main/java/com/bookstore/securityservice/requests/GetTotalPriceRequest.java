package com.bookstore.securityservice.requests;

import lombok.Builder;

import java.util.List;

@Builder
public record GetTotalPriceRequest(
        List<Long> productIds
) {
}
