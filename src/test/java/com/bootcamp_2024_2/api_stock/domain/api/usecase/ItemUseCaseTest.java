package com.bootcamp_2024_2.api_stock.domain.api.usecase;

import com.bootcamp_2024_2.api_stock.domain.exception.BrandNotFoundException;
import com.bootcamp_2024_2.api_stock.domain.exception.DuplicateCategoryException;
import com.bootcamp_2024_2.api_stock.domain.exception.IdsNotFoundException;
import com.bootcamp_2024_2.api_stock.domain.exception.InvalidCategoryCountException;
import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.model.Item;
import com.bootcamp_2024_2.api_stock.domain.spi.IBrandPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.spi.ICategoryPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.spi.IItemPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;
import com.bootcamp_2024_2.api_stock.testData.CategoryFactory;
import com.bootcamp_2024_2.api_stock.testData.ItemFactory;
import com.bootcamp_2024_2.api_stock.testData.PaginatedFactory;
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

    @Mock
    private IBrandPersistencePort iBrandPersistencePort;

    @Mock
    private ICategoryPersistencePort iCategoryPersistencePort;

    @InjectMocks
    private ItemUseCase itemUseCase;

    @Test
    @DisplayName("Save item when valid categories")
    void whenValidCategories_thenSaveItem() {
        // Given
        Item item = ItemFactory.createItemWithCategories(2);
        when(itemPersistencePort.saveItem(item)).thenReturn(item);
        when(iBrandPersistencePort.existsById(item.getBrand().getId())).thenReturn(true);
        when(iCategoryPersistencePort.existsById(anyLong())).thenReturn(true);

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

    @Test
    void testSaveItemThrowsBrandNotFoundException() {
        // Given
        Long invalidBrandId = 999L;
        Item item = ItemFactory.createItemWithCategories(2);
        item.getBrand().setId(invalidBrandId);

        when(iBrandPersistencePort.existsById(invalidBrandId)).thenReturn(false);

        // When & Then
        assertThrows(BrandNotFoundException.class, () -> itemUseCase.saveItem(item));
    }

    @Test
    void saveItem_withNonExistentCategoryIdsAndValidBrandId_shouldThrowIdsNotFoundException() {
        // Arrange
        Long validBrandId = 1L;
        Item itemWithNonExistentCategoryIds = ItemFactory.createItemWithNonExistentCategoryIds(1, validBrandId);

        when(iCategoryPersistencePort.existsById(anyLong())).thenReturn(false);
        when(iBrandPersistencePort.existsById(validBrandId)).thenReturn(true);

        // Act & Assert
        assertThrows(IdsNotFoundException.class, () -> itemUseCase.saveItem(itemWithNonExistentCategoryIds));

        verify(iCategoryPersistencePort, times(1)).existsById(anyLong());
        verify(iBrandPersistencePort, times(1)).existsById(validBrandId);
    }



    @Test
    @DisplayName("Should return sorted items based on order parameters")
    void shouldReturnSortedItems_whenDifferentOrderParameters() {
        PaginatedResult<Item> paginatedItems = PaginatedFactory.createPaginatedItems();

        int page = 1;
        int size = 10;
        boolean ascendingOrder = true;

        // Testing sorting by 'name'
        String orderByName = "name";
        when(itemPersistencePort.getAllItems(page, size, ascendingOrder, orderByName, null, null))
                .thenReturn(paginatedItems);
        PaginatedResult<Item> resultByName = itemUseCase.getAllItems(page, size, ascendingOrder, orderByName, null, null);
        assertFalse(resultByName.getItems().isEmpty());
        verify(itemPersistencePort).getAllItems(page, size, ascendingOrder, orderByName, null, null);

        // Testing sorting by 'brandname'
        String orderByBrandName = "brandname";
        when(itemPersistencePort.getAllItems(page, size, ascendingOrder, orderByBrandName, null, null))
                .thenReturn(paginatedItems);
        itemUseCase.getAllItems(page, size, ascendingOrder, orderByBrandName, null, null);
        verify(itemPersistencePort).getAllItems(page, size, ascendingOrder, orderByBrandName, null, null);
    }

    @Test
    @DisplayName("Should return items in descending order")
    void shouldReturnItems_whenDescendingOrder() {
        PaginatedResult<Item> paginatedItems = PaginatedFactory.createPaginatedItems();

        int page = 1;
        int size = 10;
        boolean ascendingOrder = false; // Descending order
        String orderByName = "name";

        when(itemPersistencePort.getAllItems(page, size, ascendingOrder, orderByName, null, null))
                .thenReturn(paginatedItems);
        PaginatedResult<Item> result = itemUseCase.getAllItems(page, size, ascendingOrder, orderByName, null, null);

        assertFalse(result.getItems().isEmpty());
        verify(itemPersistencePort).getAllItems(page, size, ascendingOrder, orderByName, null, null);
    }

    @Test
    @DisplayName("Should return items sorted by brand name")
    void shouldReturnItems_whenSortingByBrandName() {
        PaginatedResult<Item> paginatedItems = PaginatedFactory.createPaginatedItems();

        int page = 1;
        int size = 10;
        boolean ascendingOrder = true; // Ascending order
        String orderByBrandName = "brandname";

        when(itemPersistencePort.getAllItems(page, size, ascendingOrder, orderByBrandName, null, null))
                .thenReturn(paginatedItems);
        PaginatedResult<Item> result = itemUseCase.getAllItems(page, size, ascendingOrder, orderByBrandName, null, null);

        assertFalse(result.getItems().isEmpty());
        verify(itemPersistencePort).getAllItems(page, size, ascendingOrder, orderByBrandName, null, null);
    }

    @Test
    @DisplayName("Should filter items by brand ID")
    void shouldFilterItems_whenBrandIdProvided() {
        PaginatedResult<Item> paginatedItems = PaginatedFactory.createPaginatedItems();

        int page = 1;
        int size = 10;
        boolean ascendingOrder = true;
        Long brandId = 1L;

        when(itemPersistencePort.getAllItems(page, size, ascendingOrder, "name", brandId, null))
                .thenReturn(paginatedItems);

        PaginatedResult<Item> result = itemUseCase.getAllItems(page, size, ascendingOrder, "name", brandId, null);

        assertFalse(result.getItems().isEmpty());
        verify(itemPersistencePort).getAllItems(page, size, ascendingOrder, "name", brandId, null);
    }

    @Test
    @DisplayName("Should filter items by category ID")
    void shouldFilterItems_whenCategoryIdProvided() {
        PaginatedResult<Item> paginatedItems = PaginatedFactory.createPaginatedItems();

        int page = 1;
        int size = 10;
        boolean ascendingOrder = true;
        Long categoryId = 1L;

        when(itemPersistencePort.getAllItems(page, size, ascendingOrder, "name", null, categoryId))
                .thenReturn(paginatedItems);

        PaginatedResult<Item> result = itemUseCase.getAllItems(page, size, ascendingOrder, "name", null, categoryId);

        assertFalse(result.getItems().isEmpty());
        verify(itemPersistencePort).getAllItems(page, size, ascendingOrder, "name", null, categoryId);
    }


}
