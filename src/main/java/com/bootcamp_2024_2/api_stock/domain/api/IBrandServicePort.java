package com.bootcamp_2024_2.api_stock.domain.api;

import com.bootcamp_2024_2.api_stock.domain.model.Brand;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;

public interface IBrandServicePort {
    Brand saveBrand(Brand brand);

    PaginatedResult<Brand> getAllBrands(Integer page, Integer size, boolean sortDirection);

}
