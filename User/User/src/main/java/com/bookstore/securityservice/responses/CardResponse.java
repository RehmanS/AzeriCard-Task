package com.bookstore.securityservice.responses;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record CardResponse(
        String cardNumber,
        String cvv,
        BigDecimal balance,
        LocalDate expiredDate
) {

}
