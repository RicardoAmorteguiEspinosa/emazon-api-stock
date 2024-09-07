package com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request;

import com.bootcamp_2024_2.api_stock.adapters.util.constants.LengthConstants;
import com.bootcamp_2024_2.api_stock.adapters.util.constants.MessagesConstants;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AddBrandRequest (
    @NotBlank(message = MessagesConstants.NAME_CANNOT_BE_BLANK)
    @Size(min = LengthConstants.MIN_LENGTH_2, max = LengthConstants.MAX_LENGTH_50, message = MessagesConstants.NAME_LENGTH_VALIDATION)
    String name,
    @NotBlank(message = MessagesConstants.DESCRIPTION_CANNOT_BE_BLANK)
    @Size(min = LengthConstants.MIN_LENGTH_2, max = LengthConstants.MAX_LENGTH_120, message = MessagesConstants.DESCRIPTION_LENGTH_120_VALIDATION)
    String description){
}
