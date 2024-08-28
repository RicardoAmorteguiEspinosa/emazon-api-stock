package com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.CategoryResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.PaginatedCategoryResponse;
import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.model.PaginatedCategories;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ICategoryResponseMapper {
    CategoryResponse toCategoryResponse(Category category);
    List<CategoryResponse> toCategoryResponseList(List<Category> categories);

    PaginatedCategoryResponse toPaginatedCategoryResponse(PaginatedCategories allCategories);
}
