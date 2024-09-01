package com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreationResponse<T> {
    private final String message;
    private final T data;


    private CreationResponse(Builder<T> builder) {
        this.message = builder.message;
        this.data = builder.data;
    }


    public static <T> Builder<T> builder() {
        return new Builder<>();
    }


    public static class Builder<T> {
        private String message;
        private T data;

        public Builder<T> withMessage(String message) {
            this.message = message;
            return this;
        }

        public Builder<T> withData(T data) {
            this.data = data;
            return this;
        }

        public CreationResponse<T> build() {
            return new CreationResponse<>(this);
        }
    }
}
