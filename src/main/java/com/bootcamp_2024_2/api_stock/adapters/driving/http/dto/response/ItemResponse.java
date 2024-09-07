package com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@Getter
@Setter
public class ItemResponse {
    private final Long id;
    private final String name;
    private final String description;
    private final int quantity;
    private final float price;
    private final Long idBrand;
    private List<CategoryByItemResponse> categoriesList;
}