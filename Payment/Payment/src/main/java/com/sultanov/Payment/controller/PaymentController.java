package com.sultanov.Payment.controller;

import com.sultanov.Payment.dto.PaymentRequest;
import com.sultanov.Payment.dto.PaymentResponse;
import com.sultanov.Payment.service.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/payment")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping()
    public ResponseEntity<String> addPayment(@RequestBody PaymentRequest request) {
        paymentService.addPayment(request);
        return ResponseEntity.status(HttpStatus.CREATED).body("payment successfully added");
    }

    @GetMapping("/{username}")
    public List<PaymentResponse> getAllPaymentHistoryByUsername(@PathVariable("username") String username) {
        return paymentService.getAllPaymentHistoryByUsername(username);
    }
}
