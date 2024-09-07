
package com.bootcamp_2024_2.api_stock.domain.api.usecase;

import com.bootcamp_2024_2.api_stock.domain.api.IItemServicePort;
import com.bootcamp_2024_2.api_stock.domain.exception.DuplicateCategoryException;
import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.model.Item;
import com.bootcamp_2024_2.api_stock.domain.spi.IItemPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.util.validation.CategoryItemValidator;

import java.util.List;
import java.util.stream.Collectors;

public class ItemUseCase implements IItemServicePort {

    private final IItemPersistencePort iItemPersistencePort;

    public ItemUseCase(IItemPersistencePort iItemPersistencePort) {
        this.iItemPersistencePort = iItemPersistencePort;
    }

    @Override
    public Item saveItem(Item item) {
        CategoryItemValidator.validateCategories(item.getCategoriesList());
        return iItemPersistencePort.saveItem(item);
    }

}