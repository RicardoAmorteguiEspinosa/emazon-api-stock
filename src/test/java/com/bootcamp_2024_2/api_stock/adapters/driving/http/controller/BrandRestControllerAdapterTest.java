package com.bootcamp_2024_2.api_stock.adapters.driving.http.controller;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request.AddBrandRequest;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.BrandResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.CreationResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.PaginatedResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IBrandResponseMapper;
import com.bootcamp_2024_2.api_stock.configuration.exceptionhandler.ControllerAdvisor;
import com.bootcamp_2024_2.api_stock.domain.api.IBrandServicePort;
import com.bootcamp_2024_2.api_stock.domain.model.Brand;
import com.bootcamp_2024_2.api_stock.domain.util.PaginatedResult;
import com.bootcamp_2024_2.api_stock.testData.BrandFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
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
class BrandRestControllerAdapterTest {

    @Mock
    private IBrandServicePort brandServicePort;

    @Mock
    private IBrandRequestMapper brandRequestMapper;

    @Mock
    private IBrandResponseMapper brandResponseMapper;

    @InjectMocks
    private BrandRestControllerAdapter brandRestControllerAdapter;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(brandRestControllerAdapter)
                .setControllerAdvice(new ControllerAdvisor())
                .build();
    }

    @ParameterizedTest
    @MethodSource("provideValidAddBrandRequests")
    void testAddBrand_Success(AddBrandRequest request, BrandResponse expectedResponse) throws Exception {
        // Arrange
        Brand brand = BrandFactory.createBrand();
        when(brandRequestMapper.addRequestToBrand(request)).thenReturn(brand);
        when(brandServicePort.saveBrand(any(Brand.class))).thenReturn(brand);
        when(brandResponseMapper.toBrandResponse(any(Brand.class))).thenReturn(expectedResponse);

        CreationResponse<BrandResponse> creationResponse = new CreationResponse<>("The brand has been successfully recorded", expectedResponse);

        // Act & Assert
        mockMvc.perform(post("/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message").value(creationResponse.getMessage()))
                .andExpect(jsonPath("$.data.id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.data.name").value(expectedResponse.getName()))
                .andExpect(jsonPath("$.data.description").value(expectedResponse.getDescription()));
    }

    @ParameterizedTest
    @MethodSource("provideInvalidAddBrandRequests")
    void testAddBrand_BadRequest(AddBrandRequest request, String expectedMessage) throws Exception {
        // Act & Assert
        mockMvc.perform(post("/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(request)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(expectedMessage));
    }

    @Test
    @DisplayName("Get all brands when valid pagination parameters are provided")
    void getAllBrands_whenValidPaginationParameters() throws Exception {
        // Given
        int page = 0;
        int size = 10;
        boolean ascendingOrder = true;

        List<Brand> brandList = BrandFactory.createBrandList(2);
        PaginatedResult<Brand> paginate = new PaginatedResult<>(
                1,
                0,
                2,
                10,
                brandList
        );


        PaginatedResponse<BrandResponse> paginatedResponse = new PaginatedResponse<>();
        paginatedResponse.setItems(brandList.stream().map(brand ->
                new BrandResponse(brand.getId(), brand.getName(), brand.getDescription())
        ).toList());
        paginatedResponse.setTotalPages(paginate.getTotalPages());
        paginatedResponse.setCurrentPage(paginate.getCurrentPage());
        paginatedResponse.setTotalItems(paginate.getTotalItems());
        paginatedResponse.setPageSize(paginate.getPageSize());

        when(brandServicePort.getAllBrands(page, size, ascendingOrder)).thenReturn(paginate);
        when(brandResponseMapper.toPaginatedResponse(paginate)).thenReturn(paginatedResponse);

        // When & Then
        mockMvc.perform(get("/brand/")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size))
                        .param("ascendingOrder", String.valueOf(ascendingOrder))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalPages").value(paginatedResponse.getTotalPages()))
                .andExpect(jsonPath("$.currentPage").value(paginatedResponse.getCurrentPage()))
                .andExpect(jsonPath("$.totalItems").value(paginatedResponse.getTotalItems()))
                .andExpect(jsonPath("$.pageSize").value(paginatedResponse.getPageSize()))
                .andExpect(jsonPath("$.items[0].id").value(brandList.get(0).getId()))
                .andExpect(jsonPath("$.items[0].name").value(brandList.get(0).getName()))
                .andExpect(jsonPath("$.items[0].description").value(brandList.get(0).getDescription()))
                .andExpect(jsonPath("$.items[1].id").value(brandList.get(1).getId()))
                .andExpect(jsonPath("$.items[1].name").value(brandList.get(1).getName()))
                .andExpect(jsonPath("$.items[1].description").value(brandList.get(1).getDescription()));

        verify(brandServicePort).getAllBrands(page, size, ascendingOrder);
    }


    private static String asJsonString(Object obj) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


    private static Stream<Arguments> provideValidAddBrandRequests() {
        Brand brand = BrandFactory.createBrand();
        BrandResponse brandResponse = new BrandResponse(brand.getId(), brand.getName(), brand.getDescription());
        return Stream.of(
                Arguments.of(
                        new AddBrandRequest(brand.getName(), brand.getDescription()),
                        brandResponse
                )
        );
    }

    private static Stream<Arguments> provideInvalidAddBrandRequests() {
        return Stream.of(
                Arguments.of(new AddBrandRequest("x", "Valid description"), "The name must be between 2 and 50 characters"),
                Arguments.of(new AddBrandRequest("This is a very long name that exceeds the maximum allowed length", "Valid description"), "The name must be between 2 and 50 characters"),
                Arguments.of(new AddBrandRequest("Valid name", "This is a very long description that exceeds the maximum allowed length of 120 characters, which should trigger a validation error."), "Description must be between 2 and 120 characters"),
                Arguments.of(new AddBrandRequest("Valid name", "x"), "Description must be between 2 and 120 characters"),
                Arguments.of(new AddBrandRequest(null, "Valid description"), "Field 'name' cannot be null"),
                Arguments.of(new AddBrandRequest("Valid name", null), "Field 'description' cannot be null")
        );
    }

}
