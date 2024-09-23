package com.bootcamp_2024_2.api_stock.domain.api;

import com.bootcamp_2024_2.api_stock.domain.model.Item;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;

public interface IItemServicePort {
    Item saveItem(Item item);

    PaginatedResult<Item> getAllItems(Integer page, Integer size, boolean sortDirection, String order, Long brandId, Long categoryId);
}
