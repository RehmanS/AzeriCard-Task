package com.sultanov.Product.controller;

import com.sultanov.Product.dto.CheckStockRequest;
import com.sultanov.Product.dto.GetTotalPriceRequest;
import com.sultanov.Product.dto.Item;
import com.sultanov.Product.dto.UpdateStockRequest;
import com.sultanov.Product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/product")
public class ProductController {
    private final ProductService productService;

    @PostMapping
    public ResponseEntity<Boolean> checkStockAvailability(@RequestBody CheckStockRequest request){
        boolean result = productService.checkStockAvailability(request);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/totalPrice")
    public BigDecimal getTotalPrice(@RequestBody GetTotalPriceRequest request){
        return productService.getTotalPrice(request);
    }

    @PostMapping("/updateStocks")
    public ResponseEntity<String> updateStocks(@RequestBody UpdateStockRequest request) {
        productService.updateStocks(request);
        return ResponseEntity.status(HttpStatus.OK).body("Stocks successfully updated");
    }
}
