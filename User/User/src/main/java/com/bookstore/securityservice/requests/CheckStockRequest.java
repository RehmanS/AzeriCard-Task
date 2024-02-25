package com.bookstore.securityservice.requests;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
public record CheckStockRequest(
        List<Item> items
) {
}
