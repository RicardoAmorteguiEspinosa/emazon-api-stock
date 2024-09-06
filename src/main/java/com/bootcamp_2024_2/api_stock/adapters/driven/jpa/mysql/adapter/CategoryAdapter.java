package com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.util.PaginatedResult;
import com.bootcamp_2024_2.api_stock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class CategoryAdapter implements ICategoryPersistencePort {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Override
    public boolean existsByName(String name) {
        return categoryRepository.findByName(name).isPresent();
    }

    @Override
    public Category saveCategory(Category category) {
        return categoryEntityMapper.toModel(categoryRepository.save(
                categoryEntityMapper.toEntity(category)));
    }

    @Override
    public PaginatedResult<Category> getAllCategories(Integer page, Integer size, boolean ascendingOrder) {

        Sort.Direction direction = ascendingOrder ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<CategoryEntity> categoryPage = categoryRepository.findAll(PageRequest.of(page, size, Sort.by(direction, "name")));
        List<Category> categories = categoryEntityMapper.toModelList(categoryPage.getContent());

        return PaginatedResult.of(
                categoryPage.getTotalPages(),
                categoryPage.getNumber(),
                (int) categoryPage.getTotalElements(),
                categoryPage.getSize(),
                categories
        );
    }

}
