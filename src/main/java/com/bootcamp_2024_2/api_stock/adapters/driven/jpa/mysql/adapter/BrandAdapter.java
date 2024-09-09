package com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.adapter;

import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.entity.BrandEntity;
import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.mapper.IBrandEntityMapper;
import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.repository.IBrandRepository;
import com.bootcamp_2024_2.api_stock.domain.model.Brand;
import com.bootcamp_2024_2.api_stock.domain.spi.IBrandPersistencePort;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.List;

@RequiredArgsConstructor
public class BrandAdapter implements IBrandPersistencePort {
    private final IBrandRepository brandRepository;
    private final IBrandEntityMapper brandEntityMapper;

    @Override
    public boolean existsByName(String name) {
        return brandRepository.findByName(name).isPresent();
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandEntityMapper.toModel(brandRepository.save(
                brandEntityMapper.toEntity(brand)));
    }

    @Override
    public PaginatedResult<Brand> getAllBrands(Integer page, Integer size, boolean ascendingOrder) {

        Sort.Direction direction = ascendingOrder ? Sort.Direction.ASC : Sort.Direction.DESC;
        Page<BrandEntity> brandPage = brandRepository.findAll(PageRequest.of(page, size, Sort.by(direction, "name")));
        List<Brand> brands = brandEntityMapper.toModelList(brandPage.getContent());

        return PaginatedResult.of(
                brandPage.getTotalPages(),
                brandPage.getNumber(),
                (int) brandPage.getTotalElements(),
                brandPage.getSize(),
                brands
        );
    }
}
