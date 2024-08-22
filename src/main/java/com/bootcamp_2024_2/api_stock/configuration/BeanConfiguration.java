package com.bootcamp_2024_2.api_stock.configuration;

import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.adapter.CategoryAdapter;
import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.mapper.ICategoryEntityMapper;
import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.repository.ICategoryRepository;
import com.bootcamp_2024_2.api_stock.domain.api.ICategoryServicePort;
import com.bootcamp_2024_2.api_stock.domain.api.usecase.CategoryUseCase;
import com.bootcamp_2024_2.api_stock.domain.spi.ICategoryPersistencePort;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class BeanConfiguration {

    private final ICategoryRepository categoryRepository;
    private final ICategoryEntityMapper categoryEntityMapper;

    @Bean
    public ICategoryPersistencePort categoryPersistencePort() {
        return new CategoryAdapter(categoryRepository, categoryEntityMapper);
    }

    @Bean
    public ICategoryServicePort categoryServicePort() {
        return new CategoryUseCase(categoryPersistencePort());
    }

}
