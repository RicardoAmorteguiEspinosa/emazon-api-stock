package com.bootcamp_2024_2.api_stock.testData;

import com.bootcamp_2024_2.api_stock.domain.model.Brand;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BrandFactory {
    private static final Random random = new Random();

    public static Brand createBrand() {
        Long id = (long) (random.nextInt(100) + 1);
        String name = getRandomName();
        String description = "Description for " + name;
        return new Brand(id, name, description);
    }

    private static String getRandomName() {
        String[] names = new String[] { "Nike", "Adidas", "Apple", "Samsung", "Sony", "Dell", "HP", "Lenovo", "Microsoft", "Intel" };
        return names[random.nextInt(names.length)];
    }

    public static List<Brand> createBrandList(int count) {
        List<Brand> brands = new ArrayList<>();
        for (int i = 1; i <= count; i++) {
            brands.add(new Brand((long)i, getRandomName(), "Description for " + getRandomName()));
        }
        return brands;
    }
}
