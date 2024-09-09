package com.bootcamp_2024_2.api_stock.domain.api.usecase;

import com.bootcamp_2024_2.api_stock.domain.exception.ElementAlreadyExistsException;
import com.bootcamp_2024_2.api_stock.domain.model.Brand;
import com.bootcamp_2024_2.api_stock.domain.spi.IBrandPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;
import com.bootcamp_2024_2.api_stock.testData.BrandFactory;
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
class BrandUseCaseTest {
    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @Test
    void whenBrandDoesNotExist_thenSaveBrand() {
        // Given
        Brand brand = BrandFactory.createBrand();
        when(brandPersistencePort.existsByName(brand.getName())).thenReturn(false);
        when(brandPersistencePort.saveBrand(brand)).thenReturn(brand);

        // When
        Brand result = brandUseCase.saveBrand(brand);

        // Then
        assertEquals(brand, result);
        verify(brandPersistencePort).existsByName(brand.getName());
        verify(brandPersistencePort).saveBrand(brand);
    }

    @Test
    void whenBrandExists_thenThrowException() {
        // Given
        Brand brand = BrandFactory.createBrand();
        when(brandPersistencePort.existsByName(brand.getName())).thenReturn(true);

        // When & Then
        assertThrows(ElementAlreadyExistsException.class, () -> brandUseCase.saveBrand(brand));
        verify(brandPersistencePort).existsByName(brand.getName());
        verify(brandPersistencePort, never()).saveBrand(any());
    }

    @Test
    @DisplayName("Given valid pagination parameters, it should return a paginated list of brands.")
    void getAllBrands_whenValidPaginationParameters() {
        // GIVEN
        PaginatedResult<Brand> expectedPaginatedBrands = PaginatedFactory.createPaginatedBrands();

        int page = expectedPaginatedBrands.getCurrentPage();
        int size = expectedPaginatedBrands.getPageSize();
        Random random = new Random();
        boolean ascendingOrder = random.nextBoolean();

        when(brandPersistencePort.getAllBrands(page, size, ascendingOrder))
                .thenReturn(expectedPaginatedBrands);

        // WHEN
        PaginatedResult<Brand> result = brandUseCase.getAllBrands(page, size, ascendingOrder);

        // THEN
        assertEquals(expectedPaginatedBrands, result);
        verify(brandPersistencePort, times(1)).getAllBrands(page, size, ascendingOrder);
    }

    @Test
    @DisplayName("Given no brands available, it should return an empty paginated result.")
    void getAllBrands_whenNoBrandsAvailable() {
        // GIVEN
        PaginatedResult<Brand> emptyPaginatedBrands = PaginatedFactory.createPaginatedResult(Collections.emptyList(), 1, 10);

        int page = 1;
        int size = 10;
        Random random = new Random();
        boolean ascendingOrder = random.nextBoolean();

        when(brandPersistencePort.getAllBrands(page, size, ascendingOrder))
                .thenReturn(emptyPaginatedBrands);

        // WHEN
        PaginatedResult<Brand> result = brandUseCase.getAllBrands(page, size, ascendingOrder);

        // THEN
        assertTrue(result.getItems().isEmpty());
        assertEquals(0, result.getTotalItems());
        verify(brandPersistencePort, times(1)).getAllBrands(page, size, ascendingOrder);
    }

}