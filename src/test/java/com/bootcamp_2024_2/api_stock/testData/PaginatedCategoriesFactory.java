package com.bootcamp_2024_2.api_stock.testData;

import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.model.PaginatedCategories;

import java.util.List;
import java.util.Random;

public class PaginatedCategoriesFactory {

    private static final Random random = new Random();

    public static PaginatedCategories createPaginatedCategories() {

        int page = random.nextInt(10) + 1;
        int size = random.nextInt(10) + 1;
        int totalItems = size * (random.nextInt(5) + 1);
        List<Category> categories = CategoryFactory.createCategoryList(size);
        int totalPages = (int) Math.ceil((double) totalItems / size);
        return PaginatedCategories.of(totalPages, page, totalItems, size, categories);
    }
}
