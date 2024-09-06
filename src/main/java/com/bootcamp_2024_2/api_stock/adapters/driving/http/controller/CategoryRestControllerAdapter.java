package com.bootcamp_2024_2.api_stock.adapters.driving.http.controller;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request.AddCategoryRequest;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.CategoryResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.CreationResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.PaginatedResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.ICategoryRequestMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.ICategoryResponseMapper;
import com.bootcamp_2024_2.api_stock.adapters.util.HttpStatusCodes;
import com.bootcamp_2024_2.api_stock.adapters.util.Messages;
import com.bootcamp_2024_2.api_stock.adapters.util.PaginationConstants;
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

    @Operation(summary = Messages.ADD_NEW_CATEGORY_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = Messages.BRAND_ADDED_SUCCESSFULLY,
                    content = @Content(schema = @Schema(implementation = CreationResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = Messages.INVALID_REQUEST_FORMAT,
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = Messages.INTERNAL_SERVER_ERROR,
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping("/")
    public ResponseEntity<CreationResponse<CategoryResponse>> addCategory(@RequestBody @Valid AddCategoryRequest categoryRequest) {
        CategoryResponse categoryResponse = categoryResponseMapping.toCategoryResponse(
                categoryServicePort.saveCategory(categoryRequestMapping.addRequestToCategory(categoryRequest)));
        CreationResponse<CategoryResponse> response = CreationResponse.<CategoryResponse>builder()
                .withMessage(Messages.CATEGORY_SUCCESSFULLY_RECORDED)
                .withData(categoryResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = Messages.GET_ALL_CATEGORIES_WITH_PAGINATION)
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.OK, description = Messages.CATEGORIES_RETRIEVED_SUCCESSFULLY,
                    content = @Content(schema = @Schema(implementation = PaginatedResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = Messages.INTERNAL_SERVER_ERROR,
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @GetMapping("/")
    public ResponseEntity<PaginatedResponse<CategoryResponse>> getAllCategories(
            @PositiveOrZero @RequestParam(required = false, defaultValue = PaginationConstants.PAGE) Integer page,
            @Min(value = 1) @RequestParam(required = false, defaultValue = PaginationConstants.SIZE) Integer size,
            @RequestParam(required = false, defaultValue = PaginationConstants.ASCENDING_ORDER) boolean ascendingOrder) {
                return ResponseEntity.ok(categoryResponseMapping.
                toPaginatedResponse(categoryServicePort.getAllCategories(page, size, ascendingOrder)));
    }
    }
