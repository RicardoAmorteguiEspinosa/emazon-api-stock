package com.bootcamp_2024_2.api_stock.adapters.driving.http.controller;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request.AddBrandRequest;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.BrandResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.CreationResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.PaginatedResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IBrandResponseMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.util.constants.HttpStatusCodesConstants;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.util.constants.MessagesConstants;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.util.constants.PaginationConstants;
import com.bootcamp_2024_2.api_stock.configuration.exceptionhandler.ExceptionResponse;
import com.bootcamp_2024_2.api_stock.domain.api.IBrandServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestControllerAdapter {

    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapping;
    private final IBrandResponseMapper brandResponseMapper;

    @Operation(summary = MessagesConstants.ADD_NEW_BRAND_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodesConstants.CREATED, description = MessagesConstants.BRAND_ADDED_SUCCESSFULLY,
                    content = @Content(schema = @Schema(implementation = CreationResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodesConstants.BAD_REQUEST, description = MessagesConstants.INVALID_REQUEST_FORMAT,
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodesConstants.INTERNAL_SERVER_ERROR, description = MessagesConstants.INTERNAL_SERVER_ERROR,
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping("/")
    public ResponseEntity<CreationResponse<BrandResponse>> addBrand(@RequestBody @Valid AddBrandRequest brandRequest) {
        BrandResponse brandResponse = brandResponseMapper.toBrandResponse(
                brandServicePort.saveBrand(brandRequestMapping.addRequestToBrand(brandRequest)));

        CreationResponse<BrandResponse> response = CreationResponse.<BrandResponse>builder()
                .withMessage(MessagesConstants.BRAND_SUCCESSFULLY_RECORDED)
                .withData(brandResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = MessagesConstants.GET_ALL_BRANDS_WITH_PAGINATION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodesConstants.CREATED, description = MessagesConstants.BRANDS_RETRIEVED_SUCCESSFULLY,
                    content = @Content(schema = @Schema(implementation = PaginatedResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodesConstants.INTERNAL_SERVER_ERROR, description = MessagesConstants.INTERNAL_SERVER_ERROR,
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @GetMapping("/")
    public ResponseEntity<PaginatedResponse<BrandResponse>> getAllBrands(
            @PositiveOrZero @RequestParam(required = false, defaultValue = PaginationConstants.PAGE) Integer page,
            @Min(value = 1) @RequestParam(required = false, defaultValue = PaginationConstants.SIZE) Integer size,
            @RequestParam(required = false, defaultValue = PaginationConstants.ASCENDING_ORDER) boolean sortDirection) {
        return ResponseEntity.ok(brandResponseMapper.toPaginatedResponse(brandServicePort.getAllBrands(page, size, sortDirection)));
    }


}
