package com.bootcamp_2024_2.api_stock.adapters.driven.jpa.mysql.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "Category")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "category_id")
    private long id;

    private String name;

    private String description;

    @ManyToMany(mappedBy = "categoriesList")
    @JsonIgnore
    private Set<ItemEntity> itemList = new HashSet<>();
}
