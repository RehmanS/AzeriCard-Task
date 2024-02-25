package com.sultanov.Product.dto;

import java.util.List;

public record CheckStockRequest(
        List<Item> items
) {
}
