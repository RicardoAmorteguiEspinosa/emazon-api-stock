package com.bootcamp_2024_2.api_stock.domain.spi;

import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;

public interface ICategoryPersistencePort {
    boolean existsByName(String name);
    Category saveCategory(Category category);
    PaginatedResult<Category> getAllCategories(Integer page, Integer size, boolean ascendingOrder);

}
