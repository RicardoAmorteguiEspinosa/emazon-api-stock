package com.bootcamp_2024_2.api_stock.testData;

import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.model.PaginatedCategories;
import java.util.List;

public class ResponseFactory {
    public static PaginatedCategories createPaginatedCategory(int page, int size) {
        List<Category> categories = CategoryFactory.createCategoryList(size);
        return PaginatedCategories.of(1, page, size, size, categories);
    }
}
