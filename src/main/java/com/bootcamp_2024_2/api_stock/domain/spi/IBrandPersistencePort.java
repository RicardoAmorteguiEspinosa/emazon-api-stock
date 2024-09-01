package com.bootcamp_2024_2.api_stock.domain.spi;

import com.bootcamp_2024_2.api_stock.domain.model.Brand;

public interface IBrandPersistencePort {
    boolean existsByName(String name);
    Brand saveBrand(Brand brand);
}
