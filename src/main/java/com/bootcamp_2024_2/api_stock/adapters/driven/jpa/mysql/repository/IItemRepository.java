package com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.repository;

import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.entity.ItemEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IItemRepository extends JpaRepository<ItemEntity, Long> {
}