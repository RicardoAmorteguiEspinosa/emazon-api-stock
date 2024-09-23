package com.bootcamp_2024_2.api_stock.adapters.driving.http.util.validation;


import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request.AddCategoryByItemRequest;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class UniqueIdsValidator implements ConstraintValidator<UniqueIds, List<AddCategoryByItemRequest>> {

    @Override
    public boolean isValid(List<AddCategoryByItemRequest> categories, ConstraintValidatorContext context) {
        if (categories == null) {
            return true;
        }
        Set<Long> ids = new HashSet<>();
        for (AddCategoryByItemRequest category : categories) {
            if (!ids.add(category.id())) {
                return false;
            }
        }
        return true;
    }
}