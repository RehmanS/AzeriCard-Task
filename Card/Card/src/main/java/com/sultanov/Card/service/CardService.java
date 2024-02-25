package com.sultanov.Card.service;

import com.sultanov.Card.dto.CardCreateRequest;
import com.sultanov.Card.dto.CardResponse;
import com.sultanov.Card.dto.UpdateBalanceRequest;
import com.sultanov.Card.entity.Card;
import com.sultanov.Card.repository.CardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.time.LocalDate;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class CardService {

    private final CardRepository cardRepository;

    @Value("${myapp.secretKey}")
    private String secretKey;

    public List<CardResponse> findAllCardsByUsername(String username) {
        return cardRepository.findCardsByUsername(username)
                .stream()
                .map(this::cardToCardResponse)
                .toList();
    }

    public CardResponse findCardByCardNumber(String cardNumber) {
        return cardRepository.findCardByCardNumber(encrypt(cardNumber, secretKey))
                .map(this::cardToCardResponse).orElse(null);

    }

    public void addCard(CardCreateRequest request) {
        long min = 1000000000000000L;
        long max = 9999999999999999L;
        long randomCardNumber = 0L;
        int randomCVV = ThreadLocalRandom.current().nextInt(100, 999 + 1);

        while (true) {
            randomCardNumber = ThreadLocalRandom.current().nextLong(min, max + 1);
            CardResponse cardByCardNumber = findCardByCardNumber(String.valueOf(randomCardNumber));
            if (cardByCardNumber == null) {
                break;
            }
        }

        String encryptedCVV = encrypt(String.valueOf(randomCVV), secretKey);
        String encryptedCardNumber = encrypt(String.valueOf(randomCardNumber), secretKey);

        Card card = Card.builder()
                .cardNumber(encryptedCardNumber)
                .cvv(encryptedCVV)
                .balance(request.balance())
                .createdDate(LocalDate.now())
                .expiredDate(LocalDate.parse(request.expiredDate()))
                .state((byte) 1)
                .username(request.username())
                .build();
        cardRepository.save(card);
    }

    public void updateBalance(UpdateBalanceRequest request) {
        Optional<Card> cardOptional = cardRepository.findCardByCardNumber(request.cardNumber());
        cardOptional.ifPresentOrElse(card -> {
            BigDecimal newBalance = card.getBalance().subtract(request.amount());
            card.setBalance(newBalance);
            cardRepository.save(card);
        }, () -> {
            throw new RuntimeException("Card not found");
        });
    }

    public static String encrypt(String input, String secretKey) {
        byte[] salt = {1, 2, 3, 4, 5, 6, 7, 8};
        SecretKeyFactory factory = null;
        KeySpec spec = null;
        SecretKey tmp = null;
        SecretKey secret = null;
        Cipher cipher = null;
        byte[] encryptedBytes = null;

        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            spec = new PBEKeySpec(secretKey.toCharArray(), salt, 65536, 256);
            tmp = factory.generateSecret(spec);
            secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secret);
            encryptedBytes = cipher.doFinal(input.getBytes());
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }

        return Base64.getEncoder().encodeToString(encryptedBytes);

    }

    public static String decrypt(String encryptedInput, String secretKey) {
        byte[] salt = {1, 2, 3, 4, 5, 6, 7, 8};
        SecretKeyFactory factory = null;
        KeySpec spec = null;
        SecretKey tmp = null;
        SecretKey secret = null;
        Cipher cipher = null;
        byte[] decryptedBytes = null;

        try {
            factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
            spec = new PBEKeySpec(secretKey.toCharArray(), salt, 65536, 256);
            tmp = factory.generateSecret(spec);
            secret = new SecretKeySpec(tmp.getEncoded(), "AES");
            cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secret);
            decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(encryptedInput));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException |
                 InvalidKeyException | IllegalBlockSizeException | BadPaddingException e) {
            throw new RuntimeException(e);
        }

        return new String(decryptedBytes);
    }

    private CardResponse cardToCardResponse(Card card) {
        return CardResponse.builder()
                .cardNumber(decrypt(card.getCardNumber(), secretKey))
                .cvv(card.getCvv())
                .balance(card.getBalance())
                .expiredDate(card.getExpiredDate())
                .build();
    }
}