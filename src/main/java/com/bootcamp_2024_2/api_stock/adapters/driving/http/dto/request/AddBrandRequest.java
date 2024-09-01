package com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request;

import com.bootcamp_2024_2.api_stock.adapters.util.LengthConstants;
import com.bootcamp_2024_2.api_stock.adapters.util.Messages;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddBrandRequest (
    @NotBlank(message = Messages.NAME_CANNOT_BE_NULL)
    @Size(min = LengthConstants.MIN_LENGTH, max = LengthConstants.MAX_LENGTH_50, message = Messages.NAME_LENGTH_VALIDATION)
    String name,
    @NotBlank(message = Messages.DESCRIPTION_CANNOT_BE_NULL)
    @Size(min = LengthConstants.MIN_LENGTH, max = LengthConstants.MAX_LENGTH_120, message = Messages.DESCRIPTION_LENGTH_120_VALIDATION)
    String description){
}
