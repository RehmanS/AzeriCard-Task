package com.sultanov.Payment.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record UpdateStockRequest(
        List<Item> items
) {
}
