package com.bootcamp_2024_2.api_stock.adapters.util.validation;

import com.bootcamp_2024_2.api_stock.adapters.util.constants.MessagesConstants;
import jakarta.validation.Payload;
import jakarta.validation.Constraint;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = UniqueIdsValidator.class)
public @interface UniqueIds {
    String message() default MessagesConstants.CATEGORIES_ID_UNIQUE_VALIDATION;
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
