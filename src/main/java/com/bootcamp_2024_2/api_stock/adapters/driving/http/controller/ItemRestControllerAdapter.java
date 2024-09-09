package com.bootcamp_2024_2.api_stock.adapters.driving.http.controller;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request.AddItemRequest;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.CreationResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.ItemResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IItemRequestMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IItemResponseMapper;
import com.bootcamp_2024_2.api_stock.adapters.util.constants.HttpStatusCodesConstants;
import com.bootcamp_2024_2.api_stock.adapters.util.constants.MessagesConstants;
import com.bootcamp_2024_2.api_stock.configuration.exceptionhandler.ExceptionResponse;
import com.bootcamp_2024_2.api_stock.domain.api.IItemServicePort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}