package com.sultanov.Card.controller;

import com.sultanov.Card.dto.CardCreateRequest;
import com.sultanov.Card.dto.CardResponse;
import com.sultanov.Card.dto.UpdateBalanceRequest;
import com.sultanov.Card.service.CardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/card")
public class CardController {

    private final CardService cardService;

    @GetMapping
    public List<CardResponse> findAllCardsByUsername(String username){
        return cardService.findAllCardsByUsername(username);
    }

    @GetMapping("/{cardNumber}")
    public CardResponse findCardByCardNumber(@PathVariable("cardNumber") String cardNumber) {
        return cardService.findCardByCardNumber(cardNumber);
    }

    @PostMapping
    public ResponseEntity<String> addCard(@RequestBody CardCreateRequest request) throws Exception {
        cardService.addCard(request);
        return ResponseEntity.status(HttpStatus.OK).body("Card has been successfully added");
    }

    @PostMapping("/updateBalance")
    public ResponseEntity<String> updateBalance(@RequestBody UpdateBalanceRequest request){
        cardService.updateBalance(request);
        return ResponseEntity.status(HttpStatus.OK).body("Card has been successfully updated");
    }

}
