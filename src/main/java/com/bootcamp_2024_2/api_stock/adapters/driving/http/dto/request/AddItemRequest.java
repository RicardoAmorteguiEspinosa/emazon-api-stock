package com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request;


import com.bootcamp_2024_2.api_stock.adapters.util.validation.UniqueIds;
import com.bootcamp_2024_2.api_stock.adapters.util.constants.LengthConstants;
import com.bootcamp_2024_2.api_stock.adapters.util.constants.MessagesConstants;
import jakarta.validation.constraints.*;

import java.util.List;

public record AddItemRequest(
        @NotBlank(message = MessagesConstants.NAME_CANNOT_BE_BLANK)
        @Size(min = LengthConstants.MIN_LENGTH_2, max = LengthConstants.MAX_LENGTH_50,
                message = MessagesConstants.NAME_LENGTH_VALIDATION)
        String name,

        @NotBlank(message = MessagesConstants.DESCRIPTION_CANNOT_BE_BLANK)
        @Size(min =  LengthConstants.MIN_LENGTH_2, max =  LengthConstants.MAX_LENGTH_120,
                message = MessagesConstants.DESCRIPTION_LENGTH_120_VALIDATION)
        String description,

        @NotNull(message = MessagesConstants.QUANTITY_CANNOT_BE_NULL)
        @Min(value = LengthConstants.MIN_QUANTITY, message = MessagesConstants.QUANTITY_MIN_VALIDATION)
        int quantity,

        @NotNull(message = MessagesConstants.PRICE_CANNOT_BE_NULL)
        @DecimalMin(value = LengthConstants.MIN_PRICE, inclusive = false, message = MessagesConstants.PRICE_MIN_VALIDATION)
        float price,

        @NotNull(message = MessagesConstants.BRAND_ID_CANNOT_BE_NULL)
        Long idBrand,

        @NotNull(message =MessagesConstants.CATEGORIES_LIST_CANNOT_BE_NULL)
       // @Size(min = LengthConstants.MIN_CATEGORIES, max =LengthConstants.MAX_CATEGORIES,
               // message = MessagesConstants.CATEGORIES_LIST_SIZE_VALIDATION)
        //@UniqueIds
        List<AddCategoryByItemRequest> categoriesIdList) {
}
