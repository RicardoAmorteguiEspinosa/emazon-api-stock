package com.bootcamp_2024_2.api_stock.domain.model;

import java.util.List;

public  class PaginatedCategories {
    private final Integer totalPages;
    private final Integer currentPage;
    private final Integer totalItems;
    private final Integer pageSize;
    private final List<Category> categories;

    private PaginatedCategories(Integer totalPages, Integer currentPage, Integer totalItems,
                                Integer pageSize, List<Category> categories) {
        this.totalPages = totalPages;
        this.currentPage = currentPage;
        this.totalItems = totalItems;
        this.pageSize = pageSize;
        this.categories = categories;
    }

    public static PaginatedCategories of(Integer totalPages, Integer currentPage, Integer totalItems, Integer pageSize, List<Category> categories) {
        return new PaginatedCategories(totalPages, currentPage, totalItems, pageSize, categories);
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

    public List<Category> getCategories() {
        return categories;
    }
}
