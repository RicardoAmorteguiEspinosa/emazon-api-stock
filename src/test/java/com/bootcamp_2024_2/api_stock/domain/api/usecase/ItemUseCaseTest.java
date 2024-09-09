package com.bootcamp_2024_2.api_stock.domain.api.usecase;

import com.bootcamp_2024_2.api_stock.domain.exception.DuplicateCategoryException;
import com.bootcamp_2024_2.api_stock.domain.exception.InvalidCategoryCountException;
import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.model.Item;
import com.bootcamp_2024_2.api_stock.domain.spi.IItemPersistencePort;
import com.bootcamp_2024_2.api_stock.testData.CategoryFactory;
import com.bootcamp_2024_2.api_stock.testData.ItemFactory;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ItemUseCaseTest {

    @Mock
    private IItemPersistencePort itemPersistencePort;

    @InjectMocks
    private ItemUseCase itemUseCase;

    @Test
    @DisplayName("Save item when valid categories")
    void whenValidCategories_thenSaveItem() {
        // Given
        Item item = ItemFactory.createItemWithCategories(2);
        when(itemPersistencePort.saveItem(item)).thenReturn(item);

        // When
        Item result = itemUseCase.saveItem(item);

        // Then
        assertEquals(item, result);
        verify(itemPersistencePort).saveItem(item);
    }

    @Test
    @DisplayName("Throw InvalidCategoryCountException when categories are empty")
    void whenNoCategories_thenThrowInvalidCategoryCountException() {
        // Given
        Item item = ItemFactory.createItemWithCategories(0);

        // When & Then
        assertThrows(InvalidCategoryCountException.class, () -> itemUseCase.saveItem(item));
        verify(itemPersistencePort, never()).saveItem(any());
    }

    @Test
    @DisplayName("Throw InvalidCategoryCountException when more than 3 categories")
    void whenMoreThanThreeCategories_thenThrowInvalidCategoryCountException() {
        // Given
        Item item = ItemFactory.createItemWithCategories(4);

        // When & Then
        assertThrows(InvalidCategoryCountException.class, () -> itemUseCase.saveItem(item));
        verify(itemPersistencePort, never()).saveItem(any());
    }

    @Test
    @DisplayName("Throw DuplicateCategoryException when duplicate categories exist")
    void whenDuplicateCategories_thenThrowDuplicateCategoryException() {
        // Given
        Category categoryDuplicate = CategoryFactory.createCategoryId();
        Item item = ItemFactory.createItemWithDuplicateCategoryId(categoryDuplicate);

        // When & Then
        assertThrows(DuplicateCategoryException.class, () -> itemUseCase.saveItem(item));
        verify(itemPersistencePort, never()).saveItem(any());
    }
}
