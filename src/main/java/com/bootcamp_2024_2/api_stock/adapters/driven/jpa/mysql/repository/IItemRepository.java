package com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.repository;

import com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.entity.ItemEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface IItemRepository extends JpaRepository<ItemEntity, Long> {
    @Query("SELECT c FROM ItemEntity c LEFT JOIN c.categoriesList t GROUP BY c " +
            "ORDER BY COUNT(t) ASC")
    Page<ItemEntity> findAllOrderByCategories(Pageable pageable);


    Optional<ItemEntity> findByName(String name);

    @Query("SELECT i FROM ItemEntity i " +
            "JOIN i.brand b " +
            "JOIN i.categoriesList c " +
            "WHERE (:brandId IS NULL OR b.id = :brandId) " +
            "AND (:categoryId IS NULL OR c.id = :categoryId)")
    Page<ItemEntity> findAllByBrandIdAndCategoriesId(
            @Param("brandId") Long brandId,
            @Param("categoryId") Long categoryId,
            Pageable pageable);

    @Query("SELECT i FROM ItemEntity i JOIN i.brand b WHERE b.id = :brandId")
    Page<ItemEntity> findAllByBrandId(
            @Param("brandId") Long brandId,
            Pageable pageable);

    @Query("SELECT i FROM ItemEntity i JOIN i.categoriesList c WHERE c.id = :categoryId")
    Page<ItemEntity> findAllByCategoriesId(
            @Param("categoryId") Long categoryId,
            Pageable pageable);
}