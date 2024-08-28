package com.bootcamp_2024_2.api_stock.adapters.driving.http.controller;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request.AddCategoryRequest;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.PaginatedCategoryResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.ICategoryResponseMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.util.AdapterConstants;
import com.bootcamp_2024_2.api_stock.configuration.exceptionhandler.ExceptionResponse;
import com.bootcamp_2024_2.api_stock.domain.api.ICategoryServicePort;
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
@RequestMapping("/category")
@RequiredArgsConstructor
public class CategoryRestControllerAdapter {

    private final ICategoryServicePort categoryServicePort;
    private final ICategoryResponseMapper categoryResponseMapping;
    private final ICategoryRequestMapper categoryRequestMapping;

    @Operation(summary = "Add a new category to the system.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Category added successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid request format"),
            @ApiResponse(responseCode = "500", description = "Internal server error"),
    })
    @PostMapping("/")
    public ResponseEntity<String> addCategory(@RequestBody @Valid AddCategoryRequest categoryRequest) {
        categoryServicePort.saveCategory(categoryRequestMapping.addRequestToCategory(categoryRequest));
        return ResponseEntity.status(HttpStatus.CREATED).body("The category has been successfully recorded");
    }

    @Operation(summary = "Get all categories with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categories retrieved successfully",
                    content = @Content(schema = @Schema(implementation = PaginatedCategoryResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error",
            content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @GetMapping("/")
    public ResponseEntity<PaginatedCategoryResponse> getAllCategories(
       @PositiveOrZero @RequestParam(required = false, defaultValue = AdapterConstants.PAGE) Integer page,
        @Min(value = 1) @RequestParam(required = false, defaultValue = AdapterConstants.SIZE) Integer size,
            @RequestParam(required = false, defaultValue = AdapterConstants.ASCENDING_ORDER) boolean ascendingOrder) {
        return ResponseEntity.ok(categoryResponseMapping.
                toPaginatedCategoryResponse(categoryServicePort.getAllCategories(page, size, ascendingOrder)));

    }
    }
