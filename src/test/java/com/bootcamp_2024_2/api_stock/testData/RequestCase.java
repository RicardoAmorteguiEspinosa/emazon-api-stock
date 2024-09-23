package com.bootcamp_2024_2.api_stock.testData;

import org.springframework.http.HttpStatus;

public record RequestCase(String requestBody, HttpStatus expectedStatus) {
    public HttpStatus getExpectedStatus() {
        return expectedStatus;
    }

    public String getRequestBody() {
        return requestBody;
    }
}
