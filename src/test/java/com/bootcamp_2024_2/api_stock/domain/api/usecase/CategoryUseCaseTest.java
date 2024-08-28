package com.bootcamp_2024_2.api_stock.domain.api.usecase;

import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.model.PaginatedCategories;
import com.bootcamp_2024_2.api_stock.domain.spi.ICategoryPersistencePort;
import com.bootcamp_2024_2.api_stock.testData.CategoryFactory;
import com.bootcamp_2024_2.api_stock.testData.PaginatedCategoriesFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryUseCaseTest {

    private final Random random = new Random();
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

    @Test
    @DisplayName("Given valid pagination parameters, it should return a list of categories paginated.")
    void getAllCategories() {
        // GIVEN
        PaginatedCategories expectedPaginatedCategories = PaginatedCategoriesFactory.createPaginatedCategories();

        int page = expectedPaginatedCategories.getCurrentPage();
        int size = expectedPaginatedCategories.getPageSize();
        boolean ascendingOrder = random.nextBoolean();

        when(categoryPersistencePort.getAllCategories(page, size, ascendingOrder))
                .thenReturn(expectedPaginatedCategories);

        // WHEN
        PaginatedCategories result = categoryUseCase.getAllCategories(page, size, ascendingOrder);

        // THEN
        assertEquals(expectedPaginatedCategories, result);
        verify(categoryPersistencePort, times(1)).getAllCategories(page, size, ascendingOrder);
    }



}
