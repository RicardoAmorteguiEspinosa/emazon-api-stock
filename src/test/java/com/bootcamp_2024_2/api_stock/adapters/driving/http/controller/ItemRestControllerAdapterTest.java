package com.bootcamp_2024_2.api_stock.adapters.driving.http.controller;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.CategoryByItemResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.ItemResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.PaginatedResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IItemRequestMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IItemResponseMapper;
import com.bootcamp_2024_2.api_stock.domain.api.IItemServicePort;
import com.bootcamp_2024_2.api_stock.domain.model.Item;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;
import com.bootcamp_2024_2.api_stock.testData.ItemFactory;
import com.bootcamp_2024_2.api_stock.testData.RequestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static com.bootcamp_2024_2.api_stock.testData.ItemFactory.prepareItemResponses;
import static com.bootcamp_2024_2.api_stock.testData.ItemFactory.preparePaginatedResponse;
import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class ItemRestControllerAdapterTest {

    @Mock
    private IItemServicePort itemServicePort;

    @Mock
    private IItemRequestMapper itemRequestMapper;

    @Mock
    private IItemResponseMapper itemResponseMapper;

    @InjectMocks
    private ItemRestControllerAdapter itemRestControllerAdapter;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(itemRestControllerAdapter)
                .build();
    }


    @ParameterizedTest
    @MethodSource("provideItemRequests")
    void testAddItem(RequestCase testCase) throws Exception {
        // Given
        String requestBody = testCase.requestBody();
        HttpStatus expectedStatus = testCase.expectedStatus();

        // When & Then
        MvcResult mvcResult = mockMvc.perform(post("/item/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();

        if (expectedStatus == HttpStatus.CREATED) {
            verify(itemServicePort).saveItem(any());
        } else if (expectedStatus == HttpStatus.BAD_REQUEST) {
            Exception resolvedException = mvcResult.getResolvedException();
            assertInstanceOf(MethodArgumentNotValidException.class, resolvedException);
        }
    }

    @Test
    void testGetAllItems() throws Exception {
        // Given
        int page = 0;
        int size = 10;
        boolean ascendingOrder = true;
        String order = "name";
        Long brandId = null;
        Long categoryId = null;

        List<Item> itemList = ItemFactory.createItemListWithCategories(2, 3);

        PaginatedResult<Item> paginateResult = new PaginatedResult<>(
                1,
                0,
                2,
                10,
                itemList
        );

        List<ItemResponse> itemResponses = itemList.stream()
                .map(item -> {
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

        PaginatedResponse<ItemResponse> paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setItems(itemResponses);
        paginatedResponse.setTotalPages(paginateResult.getTotalPages());
        paginatedResponse.setCurrentPage(paginateResult.getCurrentPage());
        paginatedResponse.setTotalItems(paginateResult.getTotalItems());
        paginatedResponse.setPageSize(paginateResult.getPageSize());

        when(itemServicePort.getAllItems(page, size, ascendingOrder, order,brandId,categoryId)).thenReturn(paginateResult);
        when(itemResponseMapper.toPaginatedResponse(paginateResult)).thenReturn(paginatedResponse);

        // When & Then
        mockMvc.perform(get("/item/")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("ascendingOrder", String.valueOf(ascendingOrder))
                        .param("order", order)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(paginatedResponse.getTotalPages()))
                .andExpect(jsonPath("$.currentPage").value(paginatedResponse.getCurrentPage()))
                .andExpect(jsonPath("$.totalItems").value(paginatedResponse.getTotalItems()))
                .andExpect(jsonPath("$.pageSize").value(paginatedResponse.getPageSize()))
                .andExpect(jsonPath("$.items[0].id").value(itemList.get(0).getId()))
                .andExpect(jsonPath("$.items[0].name").value(itemList.get(0).getName()))
                .andExpect(jsonPath("$.items[0].description").value(itemList.get(0).getDescription()))
                .andExpect(jsonPath("$.items[0].quantity").value(itemList.get(0).getQuantity()))
                .andExpect(jsonPath("$.items[0].price").value(itemList.get(0).getPrice()))
                .andExpect(jsonPath("$.items[0].idBrand").value(itemList.get(0).getBrand().getId()))
                .andExpect(jsonPath("$.items[0].brandName").value(itemList.get(0).getBrand().getName()))
                .andExpect(jsonPath("$.items[0].categoriesList[0].id").value(itemList.get(0).getCategoriesList().get(0).getId()))
                .andExpect(jsonPath("$.items[0].categoriesList[0].name").value(itemList.get(0).getCategoriesList().get(0).getName()))
                .andExpect(jsonPath("$.items[1].id").value(itemList.get(1).getId()))
                .andExpect(jsonPath("$.items[1].name").value(itemList.get(1).getName()))
                .andExpect(jsonPath("$.items[1].description").value(itemList.get(1).getDescription()))
                .andExpect(jsonPath("$.items[1].quantity").value(itemList.get(1).getQuantity()))
                .andExpect(jsonPath("$.items[1].price").value(itemList.get(1).getPrice()))
                .andExpect(jsonPath("$.items[1].idBrand").value(itemList.get(1).getBrand().getId()))
                .andExpect(jsonPath("$.items[1].brandName").value(itemList.get(1).getBrand().getName()))
                .andExpect(jsonPath("$.items[1].categoriesList[0].id").value(itemList.get(1).getCategoriesList().get(0).getId()))
                .andExpect(jsonPath("$.items[1].categoriesList[0].name").value(itemList.get(1).getCategoriesList().get(0).getName()))
                .andReturn();

        verify(itemServicePort).getAllItems(page, size, ascendingOrder, order,brandId,categoryId);
    }

    @Test
    void testGetAllItemsWithBrandFilter() throws Exception {
        // Given
        int page = 0;
        int size = 10;
        boolean ascendingOrder = true;
        String order = "name";
        Long brandId = 1L;
        Long categoryId = null;

        List<Item> itemList = ItemFactory.createItemListWithCategories(2, 3);
        itemList = itemList.stream().filter(item -> item.getBrand().getId().equals(brandId)).toList();

        PaginatedResult<Item> paginateResult = new PaginatedResult<>(1, 0, itemList.size(), 10, itemList);

        List<ItemResponse> itemResponses = prepareItemResponses(itemList);

        PaginatedResponse<ItemResponse> paginatedResponse = preparePaginatedResponse(itemResponses, paginateResult);

        when(itemServicePort.getAllItems(page, size, ascendingOrder, order, brandId, categoryId)).thenReturn(paginateResult);
        when(itemResponseMapper.toPaginatedResponse(paginateResult)).thenReturn(paginatedResponse);

        // When & Then
        mockMvc.perform(get("/item/")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("ascendingOrder", String.valueOf(ascendingOrder))
                        .param("order", order)
                        .param("brandId", String.valueOf(brandId))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.items", hasSize(itemList.size()))); // Verifica la cantidad de ítems filtrados

        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            mockMvc.perform(get("/item/")
                            .param("page", String.valueOf(page))
                            .param("size", String.valueOf(size))
                            .param("ascendingOrder", String.valueOf(ascendingOrder))
                            .param("order", order)
                            .param("brandId", String.valueOf(brandId))
                            .contentType(MediaType.APPLICATION_JSON))
                    .andExpect(jsonPath("$.items[" + i + "].id").value(item.getId()));
        }

        verify(itemServicePort).getAllItems(page, size, ascendingOrder, order, brandId, categoryId);
    }

    private static Stream<Arguments> provideItemRequests() {
        return Stream.of(
                Arguments.of(generateRequest("{\"name\":\"Item Name\",\"description\":\"Item Description\"," +
                                "\"quantity\":10,\"price\":9.99,\"idBrand\":1,\"categoriesIdList\":[{\"id\":1}]}",
                        HttpStatus.CREATED)),

                Arguments.of(generateRequest("{\"name\":\"\",\"description\":\"Item Description\",\"quantity\":10,\"price\":9.99,\"idBrand\":1,\"categoriesIdList\":[{\"id\":1}]}",
                        HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Item Name\",\"description\":\"\",\"quantity\":10,\"price\":9.99,\"idBrand\":1,\"categoriesIdList\":[{\"id\":1}]}",
                        HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Item Name\",\"description\":\"Item Description\",\"quantity\":-1,\"price\":9.99,\"idBrand\":1,\"categoriesIdList\":[{\"id\":1}]}",
                        HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Item Name\",\"description\":\"Item Description\",\"quantity\":10,\"price\":-1.00,\"idBrand\":1,\"categoriesIdList\":[{\"id\":1}]}",
                        HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Item Name\",\"description\":\"Item Description\",\"quantity\":10,\"price\":9.99,\"idBrand\":null,\"categoriesIdList\":[{\"id\":1}]}",
                        HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Item Name\",\"description\":\"Item Description\",\"quantity\":10,\"price\":9.99,\"idBrand\":1,\"categoriesIdList\":[]}",
                        HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Item Name\",\"description\":\"Item Description\",\"quantity\":10,\"price\":9.99,\"idBrand\":1,\"categoriesIdList\":[{\"id\":1},{\"id\":2},{\"id\":3},{\"id\":4}]}",
                        HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"   Item Name   \",\"description\":\"Item Description\",\"quantity\":10,\"price\":9.99,\"idBrand\":1,\"categoriesIdList\":[{\"id\":1}]}",
                        HttpStatus.CREATED))
        );
    }

    private static RequestCase generateRequest(String requestBody, HttpStatus expectedStatus) {
        return new RequestCase(requestBody, expectedStatus);
    }

}
