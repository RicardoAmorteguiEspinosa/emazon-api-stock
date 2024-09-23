package com.bootcamp_2024_2.api_stock.domain.util.validation;

import com.bootcamp_2024_2.api_stock.domain.exception.IdsNotFoundException;
import com.bootcamp_2024_2.api_stock.domain.exception.InvalidCategoryException;
import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.spi.ICategoryPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.util.constants.LengthDomainConstants;
import com.bootcamp_2024_2.api_stock.domain.util.constants.MessagesDomainConstants;

import java.util.List;
public class CategoryValidator {

    public static void validateCategory(Category category) {
        if (category == null) {
            throw new InvalidCategoryException(MessagesDomainConstants.CATEGORY_CANNOT_BE_NULL);
        }
        FieldValidator.validateName(category.getName(), MessagesDomainConstants.CATEGORY_NAME,
                LengthDomainConstants.MIN_LENGTH_2,  LengthDomainConstants.MAX_LENGTH_50);
        FieldValidator.validateDescription(category.getDescription(), MessagesDomainConstants.CATEGORY_DESCRIPTION,
                LengthDomainConstants.MIN_LENGTH_2, LengthDomainConstants.MAX_LENGTH_90);
    }

    public  static void validateCategories(List<Category> categoriesList,ICategoryPersistencePort iCategoryPersistencePort) {
        if (categoriesList == null || categoriesList.isEmpty()) {
            throw new InvalidCategoryException(MessagesDomainConstants.CATEGORY_LIST_CANNOT_BE_EMPTY);
        }

        List<Long> categoryIds = categoriesList.stream()
                .map(Category::getId)
                .toList();

        validateCategoryIdsExist(categoryIds,iCategoryPersistencePort);
    }

    public static void validateCategoryIdsExist(List<Long> categoryIds,ICategoryPersistencePort iCategoryPersistencePort) {
        for (Long categoryId : categoryIds) {
            if (!iCategoryPersistencePort.existsById(categoryId)) {
                throw new IdsNotFoundException(MessagesDomainConstants.CATEGORY_WITH_ID + categoryId +
                        MessagesDomainConstants.NOT_FOUND_SUFFIX);
            }
        }
    }

    private CategoryValidator() {
    }
}

