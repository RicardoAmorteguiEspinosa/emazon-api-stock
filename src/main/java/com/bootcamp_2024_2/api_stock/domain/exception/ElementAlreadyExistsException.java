package com.bootcamp_2024_2.api_stock.domain.exception;

public class ElementAlreadyExistsException extends RuntimeException{
    public ElementAlreadyExistsException(String message){ super(message); }

}
