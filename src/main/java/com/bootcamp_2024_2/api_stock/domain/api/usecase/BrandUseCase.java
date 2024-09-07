package com.bootcamp_2024_2.api_stock.domain.api.usecase;

import com.bootcamp_2024_2.api_stock.domain.exception.ElementAlreadyExistsException;
import com.bootcamp_2024_2.api_stock.domain.api.IBrandServicePort;
import com.bootcamp_2024_2.api_stock.domain.model.Brand;
import com.bootcamp_2024_2.api_stock.domain.spi.IBrandPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.util.PaginatedResult;

public class BrandUseCase implements IBrandServicePort {
    private final IBrandPersistencePort brandPersistencePort;

    public BrandUseCase(IBrandPersistencePort brandPersistencePort) {
        this.brandPersistencePort = brandPersistencePort;
    }

    @Override
    public Brand saveBrand(Brand brand) {
        if (brandPersistencePort.existsByName(brand.getName())) {
            throw new ElementAlreadyExistsException(brand.getName());
        }
        return brandPersistencePort.saveBrand(brand);
    }

    @Override
    public PaginatedResult<Brand> getAllBrands(Integer page, Integer size, boolean ascendingOrder) {
        return brandPersistencePort.getAllBrands(page, size, ascendingOrder);
    }
}
