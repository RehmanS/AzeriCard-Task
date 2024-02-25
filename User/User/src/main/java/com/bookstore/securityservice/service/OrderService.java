package com.bookstore.securityservice.service;

import com.bookstore.securityservice.requests.*;
import com.bookstore.securityservice.responses.CardResponse;
import com.bookstore.securityservice.security.JwtUserDetails;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class OrderService {
    public void acceptOrder(OrderRequest request) {
        String username = getUsernameFromToken();
        Boolean isStock = stockControlFromProductService(request.items());
        if (isStock) {
            CardResponse card = cardFromCardService(request.cardNumber());
            if (card.expiredDate().isAfter(LocalDate.now()) & card.cvv().equals(request.cvv())) {
                BigDecimal totalPrice = totalPriceFromProductService(request.items());
                PaymentRequest paymentRequest = createPaymentRequest
                        (card, totalPrice, request.items(), username);
                sendOrderToPaymentService(paymentRequest);
            }
        }
    }

    private PaymentRequest createPaymentRequest
            (CardResponse card, BigDecimal totalPrice, List<Item> items, String username) {
        return PaymentRequest.builder()
                .card(card).totalPrice(totalPrice).items(items).username(username).build();
    }

    private String getUsernameFromToken() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ((JwtUserDetails) authentication.getPrincipal()).getUsername();
    }

    private CardResponse cardFromCardService(String cardNumber) {
        return WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build()
                .get()
                .uri("/api/v1/card/{cardNumber}", cardNumber)
                .retrieve()
                .bodyToMono(CardResponse.class)
                .block();
    }

    private Boolean stockControlFromProductService(List<Item> items) {

        CheckStockRequest request = CheckStockRequest.builder().items(items).build();
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build()
                .post()
                .uri("/api/v1/product")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();

    }

    private BigDecimal totalPriceFromProductService(List<Item> items) {
        List<Long> productIds = items.stream().map(Item::productId).toList();
        GetTotalPriceRequest request = GetTotalPriceRequest.builder().productIds(productIds).build();
        return WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build()
                .post()
                .uri("/api/v1/product/totalPrice")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(BigDecimal.class)
                .block();
    }

    private void sendOrderToPaymentService(PaymentRequest request) {
        WebClient.builder()
                .baseUrl("http://localhost:8082")
                .build()
                .post()
                .uri("/api/v1/payment")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }
}