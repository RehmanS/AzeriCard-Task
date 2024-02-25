package com.sultanov.Payment.service;

import com.sultanov.Payment.dto.PaymentRequest;
import com.sultanov.Payment.dto.PaymentResponse;
import com.sultanov.Payment.dto.UpdateBalanceRequest;
import com.sultanov.Payment.dto.UpdateStockRequest;
import com.sultanov.Payment.entity.Payment;
import com.sultanov.Payment.repository.PaymentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class PaymentService {
    private final PaymentRepository paymentRepository;

    public void addPayment(PaymentRequest request) {

        if (request.card().balance().compareTo(request.totalPrice()) >= 0) {

            UpdateBalanceRequest balanceRequest = createBalanceRequest(request.card().cardNumber(), request.totalPrice());
            updateBalance(balanceRequest);

            UpdateStockRequest stockRequest = UpdateStockRequest.builder().items(request.items()).build();
            updateStocks(stockRequest);
            savePayment(request);
        }
    }

    public List<PaymentResponse> getAllPaymentHistoryByUsername(String username) {
        return paymentRepository
                .findPaymentsByUsername(username)
                .stream()
                .map(this::paymentToPaymentResponse)
                .toList();
    }

    private PaymentResponse paymentToPaymentResponse(Payment payment) {
        return PaymentResponse
                .builder()
                .username(payment.getUsername())
                .amount(payment.getAmount())
                .paymentDate(payment.getPaymentDate())
                .productAndQuantity(payment.getProductAndQuantity())
                .build();
    }

    private void savePayment(PaymentRequest request) {
        Payment payment = new Payment();
        payment.setAmount(request.totalPrice());
        payment.setPaymentDate(LocalDateTime.now());
        payment.setUsername(request.username());
        Map<Long, Integer> productAndQuantity = new HashMap<>();
        request.items().forEach(item -> productAndQuantity.put(item.productId(), item.quantity()));
        payment.setProductAndQuantity(productAndQuantity);
        paymentRepository.save(payment);
    }

    private void updateBalance(UpdateBalanceRequest request) {
        WebClient.builder()
                .baseUrl("http://localhost:8080")
                .build()
                .post()
                .uri("/api/v1/card/updateBalance")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    private void updateStocks(UpdateStockRequest request) {
        WebClient.builder()
                .baseUrl("http://localhost:8081")
                .build()
                .post()
                .uri("/api/v1/product/updateStocks")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(Void.class)
                .block();
    }

    private UpdateBalanceRequest createBalanceRequest(String cardNumber, BigDecimal amount) {
        return UpdateBalanceRequest
                .builder()
                .cardNumber(cardNumber)
                .amount(amount)
                .build();
    }
}
