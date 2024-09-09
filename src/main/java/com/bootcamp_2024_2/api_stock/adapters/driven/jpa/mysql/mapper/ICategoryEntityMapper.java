package com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.mapper;

import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import com.bootcamp_2024_2.api_stock.domain.model.Category;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryEntityMapper {
    Category toModel(CategoryEntity categoryEntity);
    @Mapping(target = "itemList", ignore = true)
    CategoryEntity toEntity(Category category);
    List <Category> toModelList(List<CategoryEntity> categoryEntities);
    List<CategoryEntity> toEntityList(List<Category> categories);
}
