package com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.util.constants.LengthConstants;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.util.constants.MessagesConstants;
import com.bootcamp_2024_2.api_stock.domain.util.validation.StringUtils;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddCategoryRequest {

        @NotBlank(message = MessagesConstants.NAME_CANNOT_BE_BLANK)
        @Size(min = LengthConstants.MIN_LENGTH_2, max = LengthConstants.MAX_LENGTH_50, message = MessagesConstants.NAME_LENGTH_VALIDATION)
        private String name;

        @NotBlank(message = MessagesConstants.DESCRIPTION_CANNOT_BE_BLANK)
        @Size(min = LengthConstants.MIN_LENGTH_2, max = LengthConstants.MAX_LENGTH_90, message = MessagesConstants.DESCRIPTION_LENGTH_90_VALIDATION)
        private String description;

        public AddCategoryRequest(String name, String description) {
                this.name = StringUtils.normalizeSpaces(name);
                this.description = StringUtils.normalizeSpaces(description);
        }

}
