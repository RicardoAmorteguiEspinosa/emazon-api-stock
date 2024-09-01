package com.bootcamp_2024_2.api_stock.adapters.util;

public class Messages {
    public static final String BRAND_SUCCESSFULLY_RECORDED = "The brand has been successfully recorded";
    public static final String BRAND_ADDED_SUCCESSFULLY = "Brand added successfully";
    public static final String INVALID_REQUEST_FORMAT = "Invalid request format";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String ADD_NEW_BRAND_SUMMARY = "Add a new brand to the system.";
    public static final String ADD_NEW_CATEGORY_SUMMARY = "Add a new category to the system.";
    public static final String CATEGORY_SUCCESSFULLY_RECORDED = "The category has been successfully recorded";
    public static final String CATEGORIES_RETRIEVED_SUCCESSFULLY = "Categories retrieved successfully";
    public static final String GET_ALL_CATEGORIES_WITH_PAGINATION = "Get all categories with pagination";
    public static final String  NAME_CANNOT_BE_NULL = "Field 'name' cannot be null";
    public static final String NAME_LENGTH_VALIDATION = "The name must be between 2 and 50 characters";
    public static final String DESCRIPTION_LENGTH_90_VALIDATION = "Description must be between 2 and 90 characters";
    public static final String DESCRIPTION_LENGTH_120_VALIDATION = "Description must be between 2 and 120 characters";
    public static final String DESCRIPTION_CANNOT_BE_NULL = "Field 'description' cannot be null";
    private Messages() {
        throw new UnsupportedOperationException("The Messages class cannot be instantiated");
    }
}
