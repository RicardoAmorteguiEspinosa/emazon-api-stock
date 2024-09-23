package com.bootcamp_2024_2.api_stock.adapters.driving.http.controller;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.BrandResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.PaginatedResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IBrandResponseMapper;
import com.bootcamp_2024_2.api_stock.domain.api.IBrandServicePort;
import com.bootcamp_2024_2.api_stock.domain.model.Brand;
import com.bootcamp_2024_2.api_stock.domain.util.paginated.PaginatedResult;
import com.bootcamp_2024_2.api_stock.testData.BrandFactory;
import com.bootcamp_2024_2.api_stock.testData.RequestCase;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.bind.MethodArgumentNotValidException;

import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;


import java.util.List;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
                .build();
    }

    @ParameterizedTest
    @MethodSource("provideBrandRequests")
    void testAddBrand(RequestCase testCase) throws Exception {
        // Given
        String requestBody = testCase.getRequestBody();
        HttpStatus expectedStatus = testCase.getExpectedStatus();

        // When & Then
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/brand/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().is(expectedStatus.value()))
                .andReturn();

        if (expectedStatus == HttpStatus.CREATED) {
            verify(brandServicePort).saveBrand(any());
        } else if (expectedStatus == HttpStatus.BAD_REQUEST) {
            Exception resolvedException = mvcResult.getResolvedException();
            assertInstanceOf(MethodArgumentNotValidException.class, resolvedException);
        }
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



    private static Stream<Arguments> provideBrandRequests() {
        return Stream.of(
                Arguments.of(generateRequest("{\"name\":\"Electronics\",\"description\":\"Electronics category\"}", HttpStatus.CREATED)),

                Arguments.of(generateRequest("{\"name\":\"\",\"description\":\"Electronics category\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Electronics\",\"description\":\"\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"A\",\"description\":\"A valid description\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean\",\"description\":\"Valid description\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Valid Name\",\"description\":\"A\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"Valid Name\",\"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean. Lorem ipsum dolor sit amet, consectetur adipiscing elit. Aenean.\"}", HttpStatus.BAD_REQUEST)),

                Arguments.of(generateRequest("{\"name\":\"   Electronics   \",\"description\":\"Valid description\"}", HttpStatus.CREATED)),

                Arguments.of(generateRequest("{\"name\":\"Valid Name\",\"description\":\"   Valid description   \"}", HttpStatus.CREATED))
        );
    }

    private static RequestCase generateRequest(String requestBody, HttpStatus expectedStatus) {
        return new RequestCase(requestBody, expectedStatus);
    }


}
