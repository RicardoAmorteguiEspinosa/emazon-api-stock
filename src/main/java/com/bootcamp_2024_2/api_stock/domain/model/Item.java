package com.bootcamp_2024_2.api_stock.domain.model;

import java.util.List;

public class Item {
    private final Long id;
    private final String name;
    private final String description;
    private final int quantity;
    private final float price;
    private final Long idBrand;
    private List<Category> categoriesList;

    public Item(Long id, String name, String description, int quantity, float price, Long idBrand, List<Category> categoriesList) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.quantity = quantity;
        this.price = price;
        this.idBrand = idBrand;
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

    public Long getIdBrand() {
        return idBrand;
    }

    public List<Category> getCategoriesList() {
        return categoriesList;
    }

    public void setCategoriesList(List<Category> categoriesList) {
        this.categoriesList = categoriesList;
    }

}