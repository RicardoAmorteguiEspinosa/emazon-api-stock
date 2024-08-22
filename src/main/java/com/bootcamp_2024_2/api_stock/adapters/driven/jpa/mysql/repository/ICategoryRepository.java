package com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.repository;

import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.entity.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ICategoryRepository extends JpaRepository<CategoryEntity, Long> {
    Optional<CategoryEntity> findByName(String name);
}
