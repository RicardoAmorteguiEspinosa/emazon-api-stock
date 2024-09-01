package com.bootcamp_2024_2.api_stock.adapters.driving.http.controller;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request.AddBrandRequest;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.BrandResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.CreationResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IBrandResponseMapper;
import com.bootcamp_2024_2.api_stock.configuration.exceptionhandler.ControllerAdvisor;
import com.bootcamp_2024_2.api_stock.domain.api.IBrandServicePort;
import com.bootcamp_2024_2.api_stock.domain.model.Brand;
import com.bootcamp_2024_2.api_stock.testData.BrandFactory;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
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
                // Casos de nombre vacío
                Arguments.of(new AddBrandRequest("x", "Valid description"), "The name must be between 2 and 50 characters"),
                // Casos de descripción vacía
                Arguments.of(new AddBrandRequest("Valid name", ""), "Description must be between 2 and 120 characters"),
                // Casos de nombre demasiado largo
                Arguments.of(new AddBrandRequest("This is a very long name that exceeds the maximum allowed length", "Valid description"), "The name must be between 2 and 50 characters"),
                // Casos de descripción demasiado larga
                Arguments.of(new AddBrandRequest("Valid name", "This is a very long description that exceeds the maximum allowed length of 120 characters, which should trigger a validation error."), "Description must be between 2 and 120 characters"),
                // Casos de nombre nulo
                Arguments.of(new AddBrandRequest(null, "Valid description"), "Field 'name' cannot be null"),
                // Casos de descripción nula
                Arguments.of(new AddBrandRequest("Valid name", null), "Field 'description' cannot be null")
        );
    }

}
