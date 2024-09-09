package com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request.AddItemRequest;
import com.bootcamp_2024_2.api_stock.domain.model.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IItemRequestMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "categoriesList", source = "categoriesIdList")
    Item addRequestToItem(AddItemRequest addItemRequest);
}
