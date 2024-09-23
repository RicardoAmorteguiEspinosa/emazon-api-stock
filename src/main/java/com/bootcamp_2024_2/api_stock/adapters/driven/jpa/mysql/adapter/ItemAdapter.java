package com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.entity.ItemEntity;
import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.mapper.IItemEntityMapper;
import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.repository.IItemRepository;
import com.bootcamp_2024_2.api_stock.domain.exception.InvalidBrandException;
import com.bootcamp_2024_2.api_stock.domain.exception.InvalidCategoryException;
import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.model.Item;
import com.bootcamp_2024_2.api_stock.domain.spi.IItemPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.HashSet;
import java.util.List;

import static com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.util.SortConstants.ORDER_BY_BRAND_NAME;

@RequiredArgsConstructor
public class ItemAdapter implements IItemPersistencePort {

    private final IItemRepository itemRepository;
    private final IItemEntityMapper itemEntityMapper;
    private final ICategoryRepository categoryRepository;
    private final IBrandRepository brandRepository;

    @Override
    public boolean existsByName(String name) {
        return itemRepository.findByName(name).isPresent();
    }


    @Override
    @Transactional
    public Item saveItem(Item item) {

        BrandEntity brandEntity = brandRepository.findById(item.getBrand().getId())
                .orElseThrow(() -> new InvalidBrandException("La marca con ID " + item.getBrand().getId() + " no existe."));

        List<Long> categoryIds = item.getCategoriesList().stream()
                .map(Category::getId)
                .toList();

        List<CategoryEntity> categoryEntities = categoryRepository.findAllById(categoryIds);

        if (categoryEntities.size() != categoryIds.size()) {
            throw new InvalidCategoryException("Una o más categorías no existen.");
        }

        ItemEntity itemEntity = itemEntityMapper.toEntity(item);
        itemEntity.setBrand(brandEntity);
        itemEntity.setCategoriesList(new HashSet<>(categoryEntities));

        return itemEntityMapper.toModel(itemRepository.save(itemEntity));
    }

    @Override
    public PaginatedResult<Item> getAllItems(Integer page, Integer size, boolean sortDirection, String order, Long brandId, Long categoryId) {
        Pageable pagination = PageRequest.of(page, size, determineSort(order, sortDirection));
        Page<ItemEntity> itemPage;

        if (categoryId != null && brandId != null) {
            itemPage = itemRepository.findAllByBrandIdAndCategoriesId(brandId, categoryId, pagination);
        } else if (brandId != null) {
            itemPage = itemRepository.findAllByBrandId(brandId, pagination);
        } else if (categoryId != null) {
            itemPage = itemRepository.findAllByCategoriesId(categoryId, pagination);
        } else {
            itemPage = itemRepository.findAll(pagination);
        }

        List<Item> items = itemEntityMapper.toModelList(itemPage.getContent());

        return PaginatedResult.of(
                itemPage.getTotalPages(),
                itemPage.getNumber(),
                (int) itemPage.getTotalElements(),
                itemPage.getSize(),
                items
        );
    }

    private Sort determineSort(String order, boolean ascendingOrder) {
        Sort.Direction direction = ascendingOrder ? Sort.Direction.ASC : Sort.Direction.DESC;

        return switch (order.toLowerCase()) {
            case ORDER_BY_BRAND_NAME  -> Sort.by(direction, "brand.name");
            default -> Sort.by(direction, "name");
        };
    }

}