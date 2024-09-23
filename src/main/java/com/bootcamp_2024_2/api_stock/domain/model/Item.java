package com.bootcamp_2024_2.api_stock.domain.model;

import java.util.List;
import java.util.Optional;

public class Item {
    private final Long id;
    private final String name;
    private final String description;
    private final int quantity;
    private final float price;
    private Brand brand;
    private List<Category> categoriesList;

    public Item(Long id, String name, String description, int quantity, float price, Brand brand, List<Category> categoriesList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.brand = brand;
        this.categoriesList = categoriesList;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getQuantity() {
        return quantity;
    }

    public float getPrice() {
        return price;
    }

    public Brand getBrand() {
        return brand;
    }

    public List<Category> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

    public void setBrand(Brand brand) {
        this.brand = brand;
    }

    public Optional<Category> getCategories() {
        return categoriesList.stream().findFirst();

    }

}