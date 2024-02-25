package com.sultanov.Product.repository;

import com.sultanov.Product.dto.ProductResponse;
import com.sultanov.Product.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findAllByState(Integer state);
    Optional<Product> findProductByName(String name);
}
