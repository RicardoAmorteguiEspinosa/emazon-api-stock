package com.bootcamp_2024_2.api_stock.domain.api;

import com.bootcamp_2024_2.api_stock.domain.model.Item;

public interface IItemServicePort {
    Item saveItem(Item item);
}
