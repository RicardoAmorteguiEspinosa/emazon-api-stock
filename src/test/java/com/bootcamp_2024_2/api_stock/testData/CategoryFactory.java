package com.bootcamp_2024_2.api_stock.testData;

import com.bootcamp_2024_2.api_stock.domain.model.Category;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CategoryFactory {
    private static final Random random = new Random();

    public static Category createCategory() {
        String name = getRandomName();
        String description = "Description for " + name;
        return new Category(null, name, description);
    }

    private static String getRandomName() {
        String[] names = new String[] { "Electronics", "Clothing", "Books", "Home & Kitchen", "Sports", "Toys", "Beauty", "Automotive", "Groceries", "Health" };
        return names[random.nextInt(names.length)];
    }

    public static List<Category> createCategoryList(int count) {
        List<Category> categories = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            categories.add(new Category((long)i, getRandomName(), null));
        }
        return categories;
    }

}
