package com.bootcamp_2024_2.api_stock.domain.api;

import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.model.PaginatedCategories;

public interface ICategoryServicePort {
    void saveCategory(Category category);

    PaginatedCategories getAllCategories(Integer page, Integer size, boolean ascendingOrder);

}
