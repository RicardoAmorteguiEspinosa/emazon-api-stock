package com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class BrandResponse {
    private final Long id;
    private final String name;
    private final String description;
}
