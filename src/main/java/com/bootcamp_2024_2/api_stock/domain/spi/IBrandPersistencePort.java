package com.bootcamp_2024_2.api_stock.domain.spi;

import com.bootcamp_2024_2.api_stock.domain.model.Brand;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;

public interface IBrandPersistencePort {
    boolean existsByName(String name);
    Brand saveBrand(Brand brand);
    PaginatedResult<Brand> getAllBrands(Integer page, Integer size, boolean ascendingOrder);
}
