package com.bootcamp_2024_2.api_stock.configuration.exceptionhandler;

import com.bootcamp_2024_2.api_stock.domain.exception.DuplicateCategoryException;
import com.bootcamp_2024_2.api_stock.domain.exception.ElementAlreadyExistsException;
import com.bootcamp_2024_2.api_stock.configuration.Constants;
import com.bootcamp_2024_2.api_stock.domain.exception.InvalidCategoryCountException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.Objects;

@ControllerAdvice
@RequiredArgsConstructor
public class ControllerAdvisor {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionResponse> handleValidationExceptions(MethodArgumentNotValidException ex) {
        String message = Objects.requireNonNull(ex.getBindingResult().getFieldError()).getDefaultMessage();
        return ResponseEntity.badRequest().body(new ExceptionResponse(message == null ? Constants.INVALID_FIELD : message,
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }

    @ExceptionHandler(ElementAlreadyExistsException.class)
    public ResponseEntity<ExceptionResponse> handleElementAlreadyExistsException(ElementAlreadyExistsException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.ELEMENT_ALREADY_EXISTS_EXCEPTION_MESSAGE, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(), LocalDateTime.now()));
    }
    @ExceptionHandler(DuplicateCategoryException.class)
    public ResponseEntity<ExceptionResponse> handleDuplicateCategoryException(DuplicateCategoryException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.DUPLICATE_CATEGORY, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }
    @ExceptionHandler(InvalidCategoryCountException.class)
    public ResponseEntity<ExceptionResponse> handleInvalidCategoryCountException(InvalidCategoryCountException exception) {
        return ResponseEntity.badRequest().body(new ExceptionResponse(
                String.format(Constants.INVALID_CATEGORY_COUNT, exception.getMessage()),
                HttpStatus.BAD_REQUEST.toString(),
                LocalDateTime.now()));
    }
}
