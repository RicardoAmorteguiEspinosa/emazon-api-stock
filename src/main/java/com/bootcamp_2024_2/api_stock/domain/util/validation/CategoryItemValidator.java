package com.bootcamp_2024_2.api_stock.domain.util.validation;

import com.bootcamp_2024_2.api_stock.domain.exception.DuplicateCategoryException;
import com.bootcamp_2024_2.api_stock.domain.exception.InvalidCategoryCountException;
import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.util.constants.LengthDomainConstants;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryItemValidator{

    public static void validateCategories(List<Category> categoriesList) {

        if (categoriesList.isEmpty() || categoriesList.size() > LengthDomainConstants.MAX_CATEGORIES) {
            throw new InvalidCategoryCountException(String.valueOf(categoriesList.size()));
        }

        Map<Long, Long> categoryCounts = categoriesList.stream()
                .collect(Collectors.groupingBy(Category::getId, Collectors.counting()));

        String duplicateIds = categoryCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > LengthDomainConstants.DUPLICATE_THRESHOLD)
                .map(entry -> String.valueOf(entry.getKey()))
                .collect(Collectors.joining(", "));

        if (!duplicateIds.isEmpty()) {
            throw new DuplicateCategoryException(duplicateIds);
        }
    }

    private CategoryItemValidator() {
    }
}

