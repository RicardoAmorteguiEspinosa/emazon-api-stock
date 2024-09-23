package com.bootcamp_2024_2.api_stock.domain.util.validation;

import com.bootcamp_2024_2.api_stock.domain.exception.InvalidItemException;
import com.bootcamp_2024_2.api_stock.domain.model.Item;
import com.bootcamp_2024_2.api_stock.domain.util.constants.LengthDomainConstants;
import com.bootcamp_2024_2.api_stock.domain.util.constants.MessagesDomainConstants;

public class ItemValidator {

    public static void validateItem(Item item) {
        if (item == null) {
            throw new InvalidItemException(MessagesDomainConstants.ITEM_CANNOT_BE_NULL);
        }

        FieldValidator.validateName(item.getName(), MessagesDomainConstants.ITEM_NAME,
                LengthDomainConstants.MIN_LENGTH_2, LengthDomainConstants.MAX_LENGTH_50);

        FieldValidator.validateDescription(item.getDescription(), MessagesDomainConstants.ITEM_DESCRIPTION,
                LengthDomainConstants.MIN_LENGTH_2, LengthDomainConstants.MAX_LENGTH_120);

        validateQuantity(item.getQuantity());
        validatePrice(item.getPrice());

        if (item.getBrand() == null || item.getBrand().getId() == null) {
            throw new InvalidItemException(MessagesDomainConstants.BRAND_ID_CANNOT_BE_NULL);
        }

        CategoryItemValidator.validateCategories(item.getCategoriesList());
    }

    private static void validateQuantity(Integer quantity) {
        if (quantity != null && quantity < LengthDomainConstants.MIN_QUANTITY) {
            throw new InvalidItemException(MessagesDomainConstants.QUANTITY_MUST_BE_GREATER_THAN_ZERO);
        }
    }

    private static void validatePrice(Float price) {
        if (price == null || price <= LengthDomainConstants.MIN_PRICE) {
            throw new InvalidItemException(MessagesDomainConstants.PRICE_MUST_BE_GREATER_THAN_ZERO);
        }
    }

    private ItemValidator() {
    }
}
