package com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request;


import com.bootcamp_2024_2.api_stock.domain.util.validation.StringUtils;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.util.validation.UniqueIds;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.util.constants.LengthConstants;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.util.constants.MessagesConstants;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class AddItemRequest {
@NotBlank(message = MessagesConstants.NAME_CANNOT_BE_BLANK)
@Size(min = LengthConstants.MIN_LENGTH_2, max = LengthConstants.MAX_LENGTH_50,
        message = MessagesConstants.NAME_LENGTH_VALIDATION)
String name;

@NotBlank(message = MessagesConstants.DESCRIPTION_CANNOT_BE_BLANK)
@Size(min =  LengthConstants.MIN_LENGTH_2, max =  LengthConstants.MAX_LENGTH_120,
        message = MessagesConstants.DESCRIPTION_LENGTH_120_VALIDATION)
String description;

@NotNull(message = MessagesConstants.QUANTITY_CANNOT_BE_NULL)
@Min(value = LengthConstants.MIN_QUANTITY, message = MessagesConstants.QUANTITY_MIN_VALIDATION)
int quantity;

@NotNull(message = MessagesConstants.PRICE_CANNOT_BE_NULL)
@DecimalMin(value = LengthConstants.MIN_PRICE, inclusive = false,
        message = MessagesConstants.PRICE_MIN_VALIDATION)
float price;

@NotNull(message = MessagesConstants.BRAND_ID_CANNOT_BE_NULL)
Long idBrand;
@Size(min = LengthConstants.MIN_CATEGORIES, max =LengthConstants.MAX_CATEGORIES,
        message = MessagesConstants.CATEGORIES_LIST_SIZE_VALIDATION)
@UniqueIds
List<AddCategoryByItemRequest> categoriesIdList;

        public AddItemRequest(String name, String description, int quantity, float price, Long idBrand, List<AddCategoryByItemRequest> categoriesIdList) {
                this.name = StringUtils.normalizeSpaces(name);
                this.description = StringUtils.normalizeSpaces(description);
                this.quantity = quantity;
                this.price = price;
                this.idBrand = idBrand;
                this.categoriesIdList = categoriesIdList;
        }
}
