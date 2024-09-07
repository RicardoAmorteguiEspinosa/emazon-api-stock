package com.bootcamp_2024_2.api_stock.adapters.util.constants;

public class MessagesConstants {
    public static final String BRAND_SUCCESSFULLY_RECORDED = "The brand has been successfully recorded";
    public static final String BRAND_ADDED_SUCCESSFULLY = "Brand added successfully";
    public static final String INVALID_REQUEST_FORMAT = "Invalid request format";
    public static final String INTERNAL_SERVER_ERROR = "Internal server error";
    public static final String ADD_NEW_BRAND_SUMMARY = "Add a new brand to the system.";
    public static final String ADD_NEW_CATEGORY_SUMMARY = "Add a new category to the system.";
    public static final String CATEGORY_SUCCESSFULLY_RECORDED = "The category has been successfully recorded";
    public static final String CATEGORIES_RETRIEVED_SUCCESSFULLY = "Categories retrieved successfully";
    public static final String GET_ALL_CATEGORIES_WITH_PAGINATION = "Get all categories with pagination";
    public static final String NAME_LENGTH_VALIDATION = "The name must be between 2 and 50 characters";
    public static final String DESCRIPTION_LENGTH_90_VALIDATION = "Description must be between 2 and 90 characters";
    public static final String DESCRIPTION_LENGTH_120_VALIDATION = "Description must be between 2 and 120 characters";
    public static final String BRANDS_RETRIEVED_SUCCESSFULLY = "Brands retrieved successfully";
    public static final String GET_ALL_BRANDS_WITH_PAGINATION = "Get all brands with pagination";
    public static final String ITEM_SUCCESSFULLY_RECORDED = "The item was successfully recorded.";
    public static final String CATEGORIES_LIST_SIZE_VALIDATION = "The categories list must contain between 1 and 3 categories.";
    public static final String CATEGORIES_ID_UNIQUE_VALIDATION = "Duplicate category IDs are not allowed.";
    public static final String BRAND_ID_CANNOT_BE_NULL = "Brand ID cannot be null";
    public static final String QUANTITY_CANNOT_BE_NULL = "Quantity cannot be null";
    public static final String QUANTITY_MIN_VALIDATION = "Quantity must be at least 1";
    public static final String PRICE_CANNOT_BE_NULL = "Price cannot be null";
    public static final String PRICE_MIN_VALIDATION = "Price must be greater than 0";
    public static final String CATEGORIES_LIST_CANNOT_BE_NULL = "Categories list cannot be null";
    public static final String NAME_CANNOT_BE_BLANK = "Name cannot be blank";
    public static final String DESCRIPTION_CANNOT_BE_BLANK = "Description cannot be blank";
    public static final String ADD_NEW_ITEM_SUMMARY = "Add a new item to the system.";


    private MessagesConstants() {
        throw new UnsupportedOperationException("The Messages class cannot be instantiated");
    }
}
