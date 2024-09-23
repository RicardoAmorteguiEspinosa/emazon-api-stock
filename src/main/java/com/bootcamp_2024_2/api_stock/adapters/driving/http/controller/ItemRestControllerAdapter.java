package com.bootcamp_2024_2.api_stock.adapters.driving.http.controller;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request.AddItemRequest;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.CreationResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.ItemResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.PaginatedResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IItemRequestMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IItemResponseMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.util.constants.HttpStatusCodesConstants;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.util.constants.MessagesConstants;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.util.constants.PaginationConstants;
import com.bootcamp_2024_2.api_stock.configuration.exceptionhandler.ExceptionResponse;
import com.bootcamp_2024_2.api_stock.domain.api.IItemServicePort;
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
@RequestMapping("/item")
@RequiredArgsConstructor
public class ItemRestControllerAdapter {
    private final IItemServicePort itemServicePort;
    private final IItemRequestMapper itemRequestMapper;
    private final IItemResponseMapper itemResponseMapper;

    @Operation(summary = MessagesConstants.ADD_NEW_ITEM_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodesConstants.CREATED,
                    description = MessagesConstants.ITEM_SUCCESSFULLY_RECORDED,
                    content = @Content(schema = @Schema(implementation = CreationResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodesConstants.BAD_REQUEST,
                    description = MessagesConstants.INVALID_REQUEST_FORMAT,
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodesConstants.INTERNAL_SERVER_ERROR,
                    description = MessagesConstants.INTERNAL_SERVER_ERROR,
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class)))
    })
    @PostMapping("/")
    public ResponseEntity<CreationResponse<ItemResponse>> addItem(@RequestBody @Valid AddItemRequest itemRequest) {

        ItemResponse itemResponse = itemResponseMapper.toItemResponse(
                itemServicePort.saveItem(itemRequestMapper.addRequestToItem(itemRequest)));

        CreationResponse<ItemResponse> response = CreationResponse.<ItemResponse>builder()
                .withMessage(MessagesConstants.ITEM_SUCCESSFULLY_RECORDED)
                .withData(itemResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }


    @Operation(summary = "Get all items with pagination")
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodesConstants.CREATED, description = MessagesConstants.ITEMS_RETRIEVED_SUCCESSFULLY,
                    content = @Content(schema = @Schema(implementation = PaginatedResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodesConstants.BAD_REQUEST, description = MessagesConstants.INVALID_REQUEST_FORMAT,
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodesConstants.INTERNAL_SERVER_ERROR, description = MessagesConstants.INTERNAL_SERVER_ERROR,
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @GetMapping("/")
    public ResponseEntity<PaginatedResponse<ItemResponse>> getAllItems(
            @PositiveOrZero @RequestParam(required = false, defaultValue = PaginationConstants.PAGE) Integer page,
            @Min(value = 1) @RequestParam(required = false, defaultValue = PaginationConstants.SIZE) Integer size,
            @RequestParam(required = false, defaultValue = PaginationConstants.ASCENDING_ORDER) boolean sortDirection,
            @RequestParam(required = false, defaultValue = PaginationConstants.NAME) String order,
            @RequestParam(required = false) Long brandId,
            @RequestParam(required = false) Long categoryId) {


        PaginatedResponse<ItemResponse> paginatedResponse = itemResponseMapper.toPaginatedResponse(
                itemServicePort.getAllItems(page, size, sortDirection, order, brandId, categoryId));

        return ResponseEntity.ok(paginatedResponse);
    }

}