
package com.bootcamp_2024_2.api_stock.domain.api.usecase;

import com.bootcamp_2024_2.api_stock.domain.api.IItemServicePort;
import com.bootcamp_2024_2.api_stock.domain.exception.ElementAlreadyExistsException;
import com.bootcamp_2024_2.api_stock.domain.model.Item;
import com.bootcamp_2024_2.api_stock.domain.spi.IBrandPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.spi.ICategoryPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.spi.IItemPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;
import com.bootcamp_2024_2.api_stock.domain.util.validation.BrandValidator;
import com.bootcamp_2024_2.api_stock.domain.util.validation.CategoryValidator;
import com.bootcamp_2024_2.api_stock.domain.util.validation.ItemValidator;


public class ItemUseCase implements IItemServicePort {

    private final IItemPersistencePort iItemPersistencePort;
    private final IBrandPersistencePort iBrandPersistencePort;
    private final ICategoryPersistencePort iCategoryPersistencePort;


    public ItemUseCase(IItemPersistencePort iItemPersistencePort,IBrandPersistencePort iBrandPersistencePort,
                       ICategoryPersistencePort iCategoryPersistencePort) {
        this.iItemPersistencePort = iItemPersistencePort;
        this.iBrandPersistencePort = iBrandPersistencePort;
        this.iCategoryPersistencePort = iCategoryPersistencePort;
    }

    @Override
    public Item saveItem(Item item) {
        ItemValidator.validateItem(item);
        if(iItemPersistencePort.existsByName(item.getName()) ){
            throw new ElementAlreadyExistsException(item.getName());
        }
        BrandValidator.validateBrandExists(item.getBrand().getId(),iBrandPersistencePort);
        CategoryValidator.validateCategories(item.getCategoriesList(),iCategoryPersistencePort);

        return iItemPersistencePort.saveItem(item);
    }

    @Override
    public PaginatedResult<Item> getAllItems(Integer page, Integer size, boolean sortDirection, String order, Long brandId, Long categoryId) {
        return iItemPersistencePort.getAllItems(page,size,sortDirection,order,brandId,categoryId);
    }

}