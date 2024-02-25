package com.sultanov.Product.dto;

import java.util.List;

public record UpdateStockRequest(
        List<Item> items
) {
}
