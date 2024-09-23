package com.bootcamp_2024_2.api_stock.domain.util.validation;

import com.bootcamp_2024_2.api_stock.domain.exception.BrandNotFoundException;
import com.bootcamp_2024_2.api_stock.domain.exception.InvalidBrandException;
import com.bootcamp_2024_2.api_stock.domain.model.Brand;
import com.bootcamp_2024_2.api_stock.domain.spi.IBrandPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.util.constants.LengthDomainConstants;
import com.bootcamp_2024_2.api_stock.domain.util.constants.MessagesDomainConstants;


public class BrandValidator {
    public static void validateBrand(Brand brand) {
        if (brand == null) {
            throw new InvalidBrandException(MessagesDomainConstants.BRAND_CANNOT_BE_NULL);
        }
            FieldValidator.validateName(brand.getName(), MessagesDomainConstants.BRAND_NAME,
                    LengthDomainConstants.MIN_LENGTH_2, LengthDomainConstants.MAX_LENGTH_50);
            FieldValidator.validateDescription(brand.getDescription(), MessagesDomainConstants.BRAND_DESCRIPTION,
                    LengthDomainConstants.MIN_LENGTH_2, LengthDomainConstants.MAX_LENGTH_120);

    }
    public static void validateBrandExists(Long idBrand, IBrandPersistencePort iBrandPersistencePort) {
        if (idBrand == null || !iBrandPersistencePort.existsById(idBrand)) {
            throw new BrandNotFoundException(MessagesDomainConstants.BRAND_NOT_FOUND_MESSAGE + idBrand
                    + MessagesDomainConstants.NOT_FOUND_SUFFIX);
        }
    }
    private BrandValidator() {
    }
}