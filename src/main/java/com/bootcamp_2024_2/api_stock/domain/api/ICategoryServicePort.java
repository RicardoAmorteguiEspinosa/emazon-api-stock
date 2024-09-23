package com.bootcamp_2024_2.api_stock.domain.api;

import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;

public interface ICategoryServicePort {
    Category saveCategory(Category category);

    PaginatedResult<Category> getAllCategories(Integer page, Integer size, boolean sortDirection);

}
