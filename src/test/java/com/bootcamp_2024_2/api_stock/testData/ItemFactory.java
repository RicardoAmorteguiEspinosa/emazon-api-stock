package com.bootcamp_2024_2.api_stock.testData;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.CategoryByItemResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.ItemResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.PaginatedResponse;
import com.bootcamp_2024_2.api_stock.domain.model.Brand;
import com.bootcamp_2024_2.api_stock.domain.model.Item;
import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

public class ItemFactory {

    private static final Random random = new Random();
    private static final Long NON_EXISTENT_ID_BASE = 9999L;

    public static Item createItem(List<Category> categoriesList) {
        Long id = null;
        String name = getRandomName();
        String description = "Description for " + name;
        int quantity = random.nextInt(100) + 1;
        float price = random.nextFloat() * 100 + 1;
        Brand brand = BrandFactory.createBrand();

        return new Item(id, name, description, quantity, price, brand, categoriesList);
    }

    public static Item createItemWithCategories(int categoryCount) {
        Long id = null; // ID can be null
        String name = getRandomName();
        String description = "Description for " + name;
        int quantity = random.nextInt(100) + 1;
        float price = random.nextFloat() * 100 + 1;
        Brand brand = BrandFactory.createBrand();
        List<Category> categoriesList = CategoryFactory.createCategoryList(categoryCount); // Create categories

        return new Item(id, name, description, quantity, price, brand, categoriesList);
    }

    public static Item createItemWithDuplicateCategoryId(Category category) {
        List<Category> categories = List.of(
                category,
                category
        );
        return createItem(categories);
    }

    public static List<Item> createItemListWithCategories(int itemCount, int categoryCount) {
        return IntStream.range(0, itemCount)
                .mapToObj(i -> createItemWithCategories(categoryCount))
                .toList();
    }

    public static List<ItemResponse> prepareItemResponses(List<Item> itemList) {
        return itemList.stream().map(item -> {
            List<CategoryByItemResponse> categoryResponses = item.getCategoriesList().stream()
                    .map(category -> new CategoryByItemResponse(category.getId(), category.getName()))
                    .toList();
            return new ItemResponse(
                    item.getId(),
                    item.getName(),
                    item.getDescription(),
                    item.getQuantity(),
                    item.getPrice(),
                    item.getBrand().getId(),
                    item.getBrand().getName(),
                    categoryResponses
            );
        }).toList();
    }

    public static PaginatedResponse<ItemResponse> preparePaginatedResponse(List<ItemResponse> itemResponses, PaginatedResult<Item> paginateResult) {
        PaginatedResponse<ItemResponse> response = new PaginatedResponse<>();
        response.setItems(itemResponses);
        response.setTotalPages(paginateResult.getTotalPages());
        response.setCurrentPage(paginateResult.getCurrentPage());
        response.setTotalItems(paginateResult.getTotalItems());
        response.setPageSize(paginateResult.getPageSize());
        return response;
    }
    public static Item createItemWithNonExistentCategoryIds(int categoryCount, Long brandId) {
        // Usar solo el ID de la marca, sin necesidad de crear la marca completa
        Brand brandWithId = new Brand(brandId, null, null); // Usamos solo el ID, el resto puede ser nulo

        // Crear categorías con IDs inexistentes
        List<Category> categoriesList = IntStream.range(0, categoryCount)
                .mapToObj(i -> new Category(NON_EXISTENT_ID_BASE + random.nextInt(1000),
                        "Non-existent Category", "Description for non-existent category"))
                .toList();

        // Crear el ítem con la marca usando solo su ID y categorías con IDs inexistentes
        return new Item(null, getRandomName(), "Description for item", random.nextInt(100) + 1,
                random.nextFloat() * 100 + 1, brandWithId, categoriesList);
    }

        private static String getRandomName() {
        String[] names = new String[] { "Laptop", "Shirt", "Book", "Blender", "Soccer Ball", "Action Figure", "Shampoo", "Car Battery", "Milk", "Vitamins" };
        return names[random.nextInt(names.length)];
    }



}
