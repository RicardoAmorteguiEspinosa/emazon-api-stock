package com.bootcamp_2024_2.api_stock.domain.exception;

public class IdsNotFoundException extends RuntimeException {
    public IdsNotFoundException(String message) {
        super(message);
    }
}
