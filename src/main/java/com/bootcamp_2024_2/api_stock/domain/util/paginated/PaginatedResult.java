package com.bootcamp_2024_2.api_stock.domain.util.paginated;

import java.util.List;

public class PaginatedResult<T> {
    private final Integer totalPages;
    private final Integer currentPage;
    private final Integer totalItems;
    private final Integer pageSize;
    private final List<T> items;

    public PaginatedResult(Integer totalPages, Integer currentPage, Integer totalItems,
                            Integer pageSize, List<T> items) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.pageSize = pageSize;
        this.items = items;
    }

    public static <T> PaginatedResult<T> of(Integer totalPages, Integer currentPage, Integer totalItems, Integer pageSize, List<T> items) {
        return new PaginatedResult<>(totalPages, currentPage, totalItems, pageSize, items);
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public Integer getTotalItems() {
        return totalItems;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public List<T> getItems() {
        return items;
    }
}
