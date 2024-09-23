package com.bootcamp_2024_2.api_stock.domain.util.validation;

import com.bootcamp_2024_2.api_stock.domain.exception.InvalidFieldException;
import com.bootcamp_2024_2.api_stock.domain.util.constants.MessagesDomainConstants;

public class FieldValidator {

    public static void validateName(String name, String fieldName, int minLength, int maxLength) {
        validateField(name, fieldName, minLength, maxLength);
    }

    public static void validateDescription(String description, String fieldName, int minLength, int maxLength) {
        validateField(description, fieldName, minLength, maxLength);
    }

    private static void validateField(String value, String fieldName, int minLength, int maxLength) {
        StringUtils.normalizeSpaces(value);
        if (value == null || value.trim().isEmpty()) {
            throw new InvalidFieldException(fieldName + MessagesDomainConstants.CANNOT_BE_EMPTY);
        }
        if (value.length() < minLength || value.length() > maxLength) {
            throw new InvalidFieldException(fieldName + MessagesDomainConstants.MUST_HAVE_BETWEEN + minLength
                    + MessagesDomainConstants.AND + maxLength + MessagesDomainConstants.CHARACTERS);
        }
    }

    private FieldValidator() {
    }
}

