package com.bootcamp_2024_2.api_stock.adapters.util;

public class HttpStatusCodes {
    public static final String CREATED = "201";
    public static final String BAD_REQUEST = "400";
    public static final String INTERNAL_SERVER_ERROR = "500";
    public static final String OK = "200";

    private HttpStatusCodes() {
        throw new UnsupportedOperationException("The HttpStatusCodes class cannot be instantiated");
    }
}
