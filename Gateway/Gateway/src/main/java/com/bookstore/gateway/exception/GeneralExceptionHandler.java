package com.bookstore.gateway.exception;

import io.jsonwebtoken.MalformedJwtException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GeneralExceptionHandler {
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<?> handleBookNotFound(UnauthorizedException exception) {
        return new ResponseEntity<>(exception.getMessage(), HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(MalformedJwtException.class)
    public ResponseEntity<String> handleWrongArgument(MalformedJwtException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Malformed");
    }
}
