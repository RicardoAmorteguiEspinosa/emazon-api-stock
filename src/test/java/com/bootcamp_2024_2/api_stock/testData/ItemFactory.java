package com.bootcamp_2024_2.api_stock.testData;

import com.bootcamp_2024_2.api_stock.domain.model.Item;
import com.bootcamp_2024_2.api_stock.domain.model.Category;

import java.util.List;
import java.util.Random;

public class ItemFactory {

    private static final Random random = new Random();

    public static Item createItem(List<Category> categoriesList) {
        Long id = null; // ID can be null
        String name = getRandomName();
        String description = "Description for " + name;
        int quantity = random.nextInt(100) + 1;
        float price = random.nextFloat() * 100 + 1;
        Long idBrand = (long) (random.nextInt(10) + 1);

        return new Item(id, name, description, quantity, price, idBrand, categoriesList);
    }

    public static Item createItemWithCategories(int categoryCount) {
        Long id = null; // ID can be null
        String name = getRandomName();
        String description = "Description for " + name;
        int quantity = random.nextInt(100) + 1;
        float price = random.nextFloat() * 100 + 1;
        Long idBrand = (long) (random.nextInt(10) + 1); // Random brand ID between 1 and 10
        List<Category> categoriesList = CategoryFactory.createCategoryList(categoryCount); // Create categories

        return new Item(id, name, description, quantity, price, idBrand, categoriesList);
    }

    public static Item createItemWithDuplicateCategoryId(Category category) {
        List<Category> categories = List.of(
                category,
                category
        );
        return createItem(categories);
    }

    private static String getRandomName() {
        String[] names = new String[] { "Laptop", "Shirt", "Book", "Blender", "Soccer Ball", "Action Figure", "Shampoo", "Car Battery", "Milk", "Vitamins" };
        return names[random.nextInt(names.length)];
    }
}
