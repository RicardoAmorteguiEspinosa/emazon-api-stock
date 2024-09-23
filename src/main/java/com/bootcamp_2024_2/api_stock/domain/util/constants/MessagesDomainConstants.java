package com.bootcamp_2024_2.api_stock.domain.util.constants;

public class MessagesDomainConstants {
    public static final String BRAND_NOT_FOUND_MESSAGE = "The brand with id ";
    public static final String NOT_FOUND_SUFFIX = " does not exist.";
    public static final String BRAND_CANNOT_BE_NULL = "The brand cannot be null.";
    public static final String BRAND_NAME = "The brand name";
    public static final String BRAND_DESCRIPTION = "The brand description";
    public static final String CANNOT_BE_EMPTY = " cannot be empty.";
    public static final String MUST_HAVE_BETWEEN = " must have between ";
    public static final String CHARACTERS = " characters.";
    public static final String CATEGORY_CANNOT_BE_NULL = "The category cannot be null.";
    public static final String CATEGORY_NAME = "The category name.";
    public static final String AND = " and ";
    public static final String CATEGORY_DESCRIPTION = "The category description";
    public static final String CATEGORY_LIST_CANNOT_BE_EMPTY = "The category list cannot be empty.";
    public static final String CATEGORY_WITH_ID = "The category with ID ";
    public static final String ITEM_CANNOT_BE_NULL = "The item cannot be null.";
    public static final String ITEM_NAME = "The item name.";
    public static final String ITEM_DESCRIPTION = "The item description.";
    public static final String BRAND_ID_CANNOT_BE_NULL = "The brand ID cannot be null.";
    public static final String QUANTITY_MUST_BE_GREATER_THAN_ZERO = "The quantity must be greater than 0.";
    public static final String PRICE_MUST_BE_GREATER_THAN_ZERO = "The price must be greater than 0.";

    private MessagesDomainConstants() {
        throw new UnsupportedOperationException("The MessagesDomainConstants class cannot be instantiated");
    }
}