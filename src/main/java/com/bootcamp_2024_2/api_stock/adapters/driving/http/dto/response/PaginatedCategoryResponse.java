package com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class PaginatedCategoryResponse {
    private Integer totalPages;
    private Integer currentPage;
    private Integer totalItems;
    private Integer pageSize;
    private List<CategoryResponse> categories;
}
