package com.bootcamp_2024_2.api_stock.domain.api.usecase;

import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.spi.ICategoryPersistencePort;
import com.bootcamp_2024_2.api_stock.testData.CategoryFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Test
    @DisplayName("Given a category, it must be inserted into the database.")
    void saveCategory() {
        //GIVEN (DADO)
        Category category = CategoryFactory.createCategory();
        doNothing().when(categoryPersistencePort).saveCategory(category);

        //WHEN (CUANDO)
        categoryUseCase.saveCategory(category);

        //THEN (ENTONCES)
        verify(categoryPersistencePort, times(1)).saveCategory(category);
    }
}