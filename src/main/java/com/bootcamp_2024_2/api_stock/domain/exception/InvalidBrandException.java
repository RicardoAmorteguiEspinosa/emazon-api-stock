package com.bootcamp_2024_2.api_stock.domain.exception;

public class InvalidBrandException extends RuntimeException {
    public InvalidBrandException(String message) {
        super(message);
    }
}
