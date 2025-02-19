package com.bootcamp_2024_2.api_stock.domain.api.usecase;

import com.bootcamp_2024_2.api_stock.domain.exception.ElementAlreadyExistsException;
import com.bootcamp_2024_2.api_stock.domain.api.ICategoryServicePort;
import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;
import com.bootcamp_2024_2.api_stock.domain.spi.ICategoryPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.util.validation.CategoryValidator;


public class CategoryUseCase implements ICategoryServicePort {

    private final ICategoryPersistencePort categoryPersistencePort;

    public CategoryUseCase(ICategoryPersistencePort categoryPersistencePort) {
        this.categoryPersistencePort = categoryPersistencePort;
    }

    @Override
    public Category saveCategory(Category category) {
        CategoryValidator.validateCategory(category);

        if(categoryPersistencePort.existsByName(category.getName())) {
            throw new ElementAlreadyExistsException(category.getName());
        }
        return categoryPersistencePort.saveCategory(category);
    }

    @Override
    public PaginatedResult<Category> getAllCategories(Integer page, Integer size, boolean sortDirection) {
        return categoryPersistencePort.getAllCategories(page, size, sortDirection);
    }
}

