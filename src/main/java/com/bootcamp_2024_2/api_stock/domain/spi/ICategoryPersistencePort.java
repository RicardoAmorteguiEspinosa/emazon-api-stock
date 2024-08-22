package com.bootcamp_2024_2.api_stock.domain.spi;

import com.bootcamp_2024_2.api_stock.domain.model.Category;

public interface ICategoryPersistencePort {
    void saveCategory(Category category);
}
