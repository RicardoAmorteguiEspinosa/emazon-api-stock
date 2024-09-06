package com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PaginatedResponse<T> {
    private Integer totalPages;
    private Integer currentPage;
    private Integer totalItems;
    private Integer pageSize;
    private List<T> items;

}
