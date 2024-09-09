package com.bootcamp_2024_2.api_stock.adapters.driving.http.controller;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request.AddCategoryByItemRequest;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request.AddItemRequest;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.CategoryByItemResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.CreationResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.ItemResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IItemRequestMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IItemResponseMapper;
import com.bootcamp_2024_2.api_stock.adapters.util.constants.MessagesConstants;
import com.bootcamp_2024_2.api_stock.configuration.exceptionhandler.ControllerAdvisor;
import com.bootcamp_2024_2.api_stock.domain.api.IItemServicePort;
import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.model.Item;
import com.bootcamp_2024_2.api_stock.testData.CategoryFactory;
import com.bootcamp_2024_2.api_stock.testData.ItemFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
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
                .setControllerAdvice(new ControllerAdvisor())
                .build();
    }

    @ParameterizedTest
    @MethodSource("provideValidAddItemRequests")
    void testAddItem_Success(AddItemRequest request, ItemResponse expectedResponse) throws Exception {
        // Arrange
        Item item = ItemFactory.createItemWithCategories(3);
        when(itemRequestMapper.addRequestToItem(request)).thenReturn(item);
        when(itemServicePort.saveItem(any(Item.class))).thenReturn(item);
        when(itemResponseMapper.toItemResponse(any(Item.class))).thenReturn(expectedResponse);

        CreationResponse<ItemResponse> creationResponse = new CreationResponse<>(MessagesConstants.ITEM_SUCCESSFULLY_RECORDED, expectedResponse);

        // Act & Assert
        mockMvc.perform(post("/item/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(creationResponse.getMessage()))
                .andExpect(jsonPath("$.data.id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.data.name").value(expectedResponse.getName()))
                .andExpect(jsonPath("$.data.description").value(expectedResponse.getDescription()));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidAddItemRequests")
    void testAddItem_BadRequest(AddItemRequest request, String expectedMessage) throws Exception {
        // Act & Assert
        mockMvc.perform(post("/item/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    private static String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static Stream<Arguments> provideValidAddItemRequests() {
        // Crear Item con categorías
        List<Category> categories = CategoryFactory.createCategoryList(3);
        Item item = ItemFactory.createItem(categories);

        // Mapear categorías a CategoryByItemResponse
        List<CategoryByItemResponse> categoryByItemResponses = categories.stream()
                .map(category -> new CategoryByItemResponse(category.getId()))
                .toList();

        // Crear ItemResponse usando la lista mapeada
        ItemResponse itemResponse = new ItemResponse(
                item.getId(),
                item.getName(),
                item.getDescription(),
                item.getQuantity(),
                item.getPrice(),
                item.getIdBrand(),
                categoryByItemResponses
        );

        // Crear AddItemRequest
        AddItemRequest addItemRequest = new AddItemRequest(
                item.getName(),
                item.getDescription(),
                item.getQuantity(),
                item.getPrice(),
                item.getIdBrand(),
                categories.stream()
                        .map(category -> new AddCategoryByItemRequest(category.getId()))
                        .toList()
        );

        return Stream.of(
                Arguments.of(addItemRequest, itemResponse)
        );
    }

    private static Stream<Arguments> provideInvalidAddItemRequests() {
        AddCategoryByItemRequest category = new AddCategoryByItemRequest(1L);
        return Stream.of(
                // Validaciones de nombre
                Arguments.of(new AddItemRequest("a", "Valid description", 10, 20.0f,
                        1L, List.of(category)), MessagesConstants.NAME_LENGTH_VALIDATION),
                Arguments.of(new AddItemRequest("Valid name", "a", 10, 20.0f,
                        1L, List.of(category)), MessagesConstants.DESCRIPTION_LENGTH_120_VALIDATION),
                Arguments.of(new AddItemRequest("Valid name", "Valid description", -1, 20.0f,
                        1L, List.of(category)), MessagesConstants.QUANTITY_MIN_VALIDATION),
                Arguments.of(new AddItemRequest("Valid name", "Valid description", 0, 20.0f,
                        1L, List.of(category)), MessagesConstants.QUANTITY_MIN_VALIDATION),
                Arguments.of(new AddItemRequest("Valid name", "Valid description", 10, -1.0f,
                        1L, List.of(category)), MessagesConstants.PRICE_MIN_VALIDATION),
                Arguments.of(new AddItemRequest("Valid name", "Valid description", 10, 0.0f,
                        1L, List.of(category)), MessagesConstants.PRICE_MIN_VALIDATION),
                Arguments.of(new AddItemRequest("Valid name", "Valid description", 10, 20.0f,
                        null, List.of(category)), MessagesConstants.BRAND_ID_CANNOT_BE_NULL),
                Arguments.of(new AddItemRequest("Valid name", "Valid description", 10, 20.0f,
                        1L, List.of()), MessagesConstants.CATEGORIES_LIST_SIZE_VALIDATION),
                Arguments.of(new AddItemRequest("Valid name", "Valid description", 10, 20.0f,
                        1L, List.of(new AddCategoryByItemRequest(2L), category, new AddCategoryByItemRequest(3L),
                        new AddCategoryByItemRequest(4L))), MessagesConstants.CATEGORIES_LIST_SIZE_VALIDATION),
                Arguments.of(new AddItemRequest("Valid name", "Valid description", 10, 20.0f,
                        1L, List.of(category,category)), MessagesConstants.CATEGORIES_ID_UNIQUE_VALIDATION)
        );
    }

}
