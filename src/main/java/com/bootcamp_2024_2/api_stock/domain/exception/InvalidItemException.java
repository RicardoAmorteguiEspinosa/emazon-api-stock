package com.bootcamp_2024_2.api_stock.domain.exception;

public class InvalidItemException extends RuntimeException {
    public InvalidItemException(String message) {
        super(message);
    }
}
