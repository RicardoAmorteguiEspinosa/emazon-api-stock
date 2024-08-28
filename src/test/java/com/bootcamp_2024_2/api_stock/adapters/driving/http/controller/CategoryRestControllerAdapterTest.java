package com.bootcamp_2024_2.api_stock.adapters.driving.http.controller;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.CategoryResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.PaginatedCategoryResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.ICategoryResponseMapper;
import com.bootcamp_2024_2.api_stock.domain.api.ICategoryServicePort;
import com.bootcamp_2024_2.api_stock.domain.model.Category;
import com.bootcamp_2024_2.api_stock.domain.model.PaginatedCategories;
import com.bootcamp_2024_2.api_stock.testData.RequestCase;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class CategoryRestControllerAdapterTest {
    @Mock
    private ICategoryServicePort categoryServicePort;
    @Mock
    private ICategoryRequestMapper categoryRequestMapper;
    @Mock
    private ICategoryResponseMapper categoryResponseMapper;
    @InjectMocks
    private CategoryRestControllerAdapter categoryRestControllerAdapter;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(categoryRestControllerAdapter).build();
    }

    @ParameterizedTest
    @MethodSource("provideCategoryRequests")
    void testAddCategory(RequestCase testCase) throws Exception {
        // Given
        String requestBody = testCase.getRequestBody();
        HttpStatus expectedStatus = testCase.getExpectedStatus();

        // When & Then
        MvcResult mvcResult = mockMvc.perform(post("/category/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();

        if (expectedStatus == HttpStatus.CREATED) {
            verify(categoryServicePort).saveCategory(any());
        } else if (expectedStatus == HttpStatus.BAD_REQUEST) {
            Exception resolvedException = mvcResult.getResolvedException();
            assertTrue(resolvedException instanceof MethodArgumentNotValidException);
        }
    }

    @ParameterizedTest
    @MethodSource("provideValidGetAllCategoriesParams")
    void testGetAllCategories(Integer page, Integer size, Boolean ascendingOrder, HttpStatus expectedStatus, PaginatedCategoryResponse expectedResponse) throws Exception {
        // Arrange
        PaginatedCategories paginatedCategories = PaginatedCategories.of(
                expectedResponse.getTotalPages(),
                expectedResponse.getCurrentPage(),
                expectedResponse.getTotalItems(),
                expectedResponse.getPageSize(),
                expectedResponse.getCategories().stream()
                        .map(cr -> new Category(cr.getId(), cr.getName(), cr.getDescription()))
                        .toList()
        );

        when(categoryServicePort.getAllCategories(page, size, ascendingOrder))
                .thenReturn(paginatedCategories);
        when(categoryResponseMapper.toPaginatedCategoryResponse(paginatedCategories))
                .thenReturn(expectedResponse);

        // Act & Assert
         mockMvc.perform(get("/category/")
                        .param("page", page != null ? page.toString() : "0")
                        .param("size", size != null ? size.toString() : "10")
                        .param("ascendingOrder", ascendingOrder != null ? ascendingOrder.toString() : "true")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().is(expectedStatus.value()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalPages").value(expectedResponse.getTotalPages()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.currentPage").value(expectedResponse.getCurrentPage()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.totalItems").value(expectedResponse.getTotalItems()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.pageSize").value(expectedResponse.getPageSize()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories.length()").value(expectedResponse.getCategories().size()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].id").value(expectedResponse.getCategories().get(0).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].name").value(expectedResponse.getCategories().get(0).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[0].description").value(expectedResponse.getCategories().get(0).getDescription()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[1].id").value(expectedResponse.getCategories().get(1).getId()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[1].name").value(expectedResponse.getCategories().get(1).getName()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.categories[1].description").value(expectedResponse.getCategories().get(1).getDescription()))
                .andReturn();
    }

    @ParameterizedTest
    @MethodSource("provideInvalidGetAllCategoriesParams")
    void testGetAllCategories_ErrorCases(Integer page, Integer size, boolean ascendingOrder) throws Exception {
        // When & Then
        mockMvc.perform(get("/category/")
                        .param("page", page != null ? page.toString() : "0")
                        .param("size", size != null ? size.toString() : "2")
                        .param("ascendingOrder", Boolean.toString(ascendingOrder))
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private static Stream<Arguments> provideInvalidGetAllCategoriesParams() {
        return Stream.of(
                Arguments.of(-1, 2, true),  // `page` negativo
                Arguments.of(0, 0, true)    // `size` cero
        );
    }

    private static Stream<Arguments> provideValidGetAllCategoriesParams() {
        return Stream.of(
                Arguments.of(0, 2, true, HttpStatus.OK, new PaginatedCategoryResponse(
                        1, 0, 2, 2, Arrays.asList(
                        new CategoryResponse(1L, "Electronics", "Various electronic devices"),
                        new CategoryResponse(2L, "Books", "Different genres of books")
                ))),
                Arguments.of(0, 2, false, HttpStatus.OK, new PaginatedCategoryResponse(
                        1, 0, 2, 2, Arrays.asList(
                        new CategoryResponse(1L, "Books", "Different genres of books"),
                        new CategoryResponse(2L, "Electronics", "Various electronic devices")
                ))),
                Arguments.of(0, 10, true, HttpStatus.OK, new PaginatedCategoryResponse(
                        1, 0, 2, 2, Arrays.asList(
                        new CategoryResponse(1L, "Electronics", "Various electronic devices"),
                        new CategoryResponse(2L, "Books", "Different genres of books")
                )))
        );
    }
    private static Stream<Arguments> provideCategoryRequests() {
        return Stream.of(
                Arguments.of(generateRequest("{\"name\":\"Electronics\",\"description\":\"Electronics category\"}", HttpStatus.CREATED)),

                Arguments.of(generateRequest("{\"name\":\"\",\"description\":\"Electronics category\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Electronics\",\"description\":\"\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"A\",\"description\":\"A\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean.\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean.\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean\",\"description\":\"Electronics category\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Electronics\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean.Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean.\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"H\",\"description\":\"H\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Electronics\",\"description\":\" Electronics\"}",HttpStatus.CREATED))
        );
    }

    private static RequestCase generateRequest(String requestBody, HttpStatus expectedStatus) {
        return new RequestCase(requestBody, expectedStatus);
    }
}