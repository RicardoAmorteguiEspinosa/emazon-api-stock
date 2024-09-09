package com.bootcamp_2024_2.api_stock.domain.exception;

public class InvalidCategoryCountException extends RuntimeException {
    public InvalidCategoryCountException(String message) {
        super(message);
    }
}