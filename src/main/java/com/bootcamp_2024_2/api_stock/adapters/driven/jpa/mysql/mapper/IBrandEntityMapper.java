package com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.mapper;

import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamp_2024_2.api_stock.domain.model.Brand;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IBrandEntityMapper {
    BrandEntity toEntity(Brand brand);
    Brand toModel(BrandEntity brandEntity);

}
