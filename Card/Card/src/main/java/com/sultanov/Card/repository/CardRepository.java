package com.sultanov.Card.repository;

import com.sultanov.Card.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CardRepository extends JpaRepository<Card, Long> {
    List<Card> findCardsByUsername(String username);
    Optional<Card> findCardByCardNumber(String cardNumber);
}
