package com.bootcamp_2024_2.api_stock.domain.spi;

import com.bootcamp_2024_2.api_stock.domain.model.Item;

public interface IItemPersistencePort {
    Item saveItem(Item item);
}
