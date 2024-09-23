package com.bootcamp_2024_2.api_stock.domain.api.usecase;
import com.bootcamp_2024_2.api_stock.domain.exception.ElementAlreadyExistsException;
import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.spi.ICategoryPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;
import com.bootcamp_2024_2.api_stock.testData.CategoryFactory;
import com.bootcamp_2024_2.api_stock.testData.PaginatedFactory;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    @Mock
    private ICategoryPersistencePort categoryPersistencePort;

    @InjectMocks
    private CategoryUseCase categoryUseCase;

    @Test
    void whenCategoryDoesNotExist_thenSaveCategory() {
        // Given
        Category category = CategoryFactory.createCategory();
        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(false);
        when(categoryPersistencePort.saveCategory(category)).thenReturn(category);

        // When
        Category result = categoryUseCase.saveCategory(category);

        // Then
        assertEquals(category, result);
        verify(categoryPersistencePort).existsByName(category.getName());
        verify(categoryPersistencePort).saveCategory(category);
    }

    @Test
    void whenCategoryExists_thenThrowException() {
        // Given
        Category category = CategoryFactory.createCategory();
        when(categoryPersistencePort.existsByName(category.getName())).thenReturn(true);

        // When & Then
        assertThrows(ElementAlreadyExistsException.class, () -> categoryUseCase.saveCategory(category));
        verify(categoryPersistencePort).existsByName(category.getName());
        verify(categoryPersistencePort, never()).saveCategory(any());
    }

    @Test
    @DisplayName("Given valid pagination parameters, it should return a paginated list of categories.")
    void getAllCategories_whenValidPaginationParameters() {
        // GIVEN
        PaginatedResult<Category> expectedPaginatedCategories = PaginatedFactory.createPaginatedCategories();

        int page = expectedPaginatedCategories.getCurrentPage();
        int size = expectedPaginatedCategories.getPageSize();
        Random random = new Random();
        boolean ascendingOrder = random.nextBoolean();

        when(categoryPersistencePort.getAllCategories(page, size, ascendingOrder))
                .thenReturn(expectedPaginatedCategories);

        // WHEN
        PaginatedResult<Category> result = categoryUseCase.getAllCategories(page, size, ascendingOrder);

        // THEN
        assertEquals(expectedPaginatedCategories, result);
        verify(categoryPersistencePort, times(1)).getAllCategories(page, size, ascendingOrder);
    }

    @Test
    @DisplayName("Given no categories available, it should return an empty paginated result.")
    void getAllCategories_whenNoCategoriesAvailable() {
        // GIVEN
        PaginatedResult<Category> emptyPaginatedCategories = PaginatedFactory.createPaginatedResult(Collections.emptyList(), 1, 10);

        int page = 1;
        int size = 10;
        Random random = new Random();
        boolean ascendingOrder = random.nextBoolean();

        when(categoryPersistencePort.getAllCategories(page, size, ascendingOrder))
                .thenReturn(emptyPaginatedCategories);

        // WHEN
        PaginatedResult<Category> result = categoryUseCase.getAllCategories(page, size, ascendingOrder);

        // THEN
        assertTrue(result.getItems().isEmpty());
        assertEquals(0, result.getTotalItems());
        verify(categoryPersistencePort, times(1)).getAllCategories(page, size, ascendingOrder);
    }
}

