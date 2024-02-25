package com.sultanov.Product.dto;

import java.util.List;

public record GetTotalPriceRequest(
        List<Long> productIds
) {
}
