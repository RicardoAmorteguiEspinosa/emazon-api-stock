package com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.ItemResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.PaginatedResponse;
import com.bootcamp_2024_2.api_stock.domain.model.Item;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface IItemResponseMapper {
    @Mapping(source = "categoriesList", target = "categoriesList")
    @Mapping(source = "brand.id", target = "idBrand")
    @Mapping(source = "brand.name", target = "brandName")
    ItemResponse toItemResponse(Item item);

    List<ItemResponse> toItemResponseList(List<Item> items);

    PaginatedResponse<ItemResponse> toPaginatedResponse(PaginatedResult<Item> paginatedResult);
}