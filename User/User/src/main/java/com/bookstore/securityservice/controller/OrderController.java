package com.bookstore.securityservice.controller;

import com.bookstore.securityservice.requests.OrderRequest;
import com.bookstore.securityservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/order")
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<String> addOrder(@RequestBody OrderRequest request){
        orderService.acceptOrder(request);
        return ResponseEntity.status(HttpStatus.OK).body("Order accepted");
    }
}
