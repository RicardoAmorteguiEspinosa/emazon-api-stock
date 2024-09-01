package com.bootcamp_2024_2.api_stock.domain.api.usecase;

import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.exception.ElementAlreadyExistsException;
import com.bootcamp_2024_2.api_stock.domain.model.Brand;
import com.bootcamp_2024_2.api_stock.domain.spi.IBrandPersistencePort;
import com.bootcamp_2024_2.api_stock.testData.BrandFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BrandUseCaseTest {
    @Mock
    private IBrandPersistencePort brandPersistencePort;

    @InjectMocks
    private BrandUseCase brandUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


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
}