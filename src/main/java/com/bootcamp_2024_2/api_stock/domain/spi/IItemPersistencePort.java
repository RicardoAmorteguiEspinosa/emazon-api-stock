package com.bootcamp_2024_2.api_stock.domain.spi;

import com.bootcamp_2024_2.api_stock.domain.model.Item;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;

public interface IItemPersistencePort {
    Item saveItem(Item item);
    boolean existsByName(String name);

    PaginatedResult<Item> getAllItems(Integer page, Integer size, boolean sortDirection, String order, Long brandId, Long categoryId);

}
