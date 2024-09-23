package com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.mapper;

import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.entity.ItemEntity;
import com.bootcamp_2024_2.api_stock.domain.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IItemEntityMapper {
    @Mapping(source = "categoriesList", target = "categoriesList")
    @Mapping(source = "brand.id", target = "brand.id")
    ItemEntity toEntity(Item item);
   @Mapping(source = "categoriesList", target = "categoriesList")
   @Mapping(source = "brand.id", target = "brand.id")
   @Mapping(source = "brand.name", target = "brand.name")
   Item toModel(ItemEntity itemEntity);

    List<Item> toModelList(List<ItemEntity> itemEntities);
}