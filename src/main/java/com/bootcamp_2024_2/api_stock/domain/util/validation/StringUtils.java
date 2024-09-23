package com.bootcamp_2024_2.api_stock.domain.util.validation;

public class StringUtils {

    public static String normalizeSpaces(String value) {
        if (value == null) {
            return null;
        }
        return value.trim().replaceAll("\\s+", " ");
    }
    private StringUtils() {
    }
}
