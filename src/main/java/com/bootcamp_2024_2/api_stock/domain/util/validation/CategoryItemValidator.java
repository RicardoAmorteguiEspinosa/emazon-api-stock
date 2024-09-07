package com.bootcamp_2024_2.api_stock.domain.util.validation;

import com.bootcamp_2024_2.api_stock.domain.exception.DuplicateCategoryException;
import com.bootcamp_2024_2.api_stock.domain.exception.InvalidCategoryCountException;
import com.bootcamp_2024_2.api_stock.domain.model.Category;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CategoryItemValidator {

    public static void validateCategories(List<Category> categoriesList) {
        Map<Long, Long> categoryCounts = categoriesList.stream()
                .collect(Collectors.groupingBy(Category::getId, Collectors.counting()));

        String numberOfCategories = String.valueOf(categoriesList.size());

        if (categoriesList.isEmpty() || categoriesList.size() > 3) {
            throw new InvalidCategoryCountException(numberOfCategories);
        }

        String duplicateIds = categoryCounts.entrySet().stream()
                .filter(entry -> entry.getValue() > 1)
                .map(entry -> String.valueOf(entry.getKey()))
                .collect(Collectors.joining(", "));

        if (!duplicateIds.isEmpty()) {
            throw new DuplicateCategoryException(duplicateIds);
        }
    }
}

