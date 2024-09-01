package com.bootcamp_2024_2.api_stock.adapters.driving.http.controller;

import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.request.AddBrandRequest;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.BrandResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.dto.response.CreationResponse;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IBrandRequestMapper;
import com.bootcamp_2024_2.api_stock.adapters.driving.http.mapper.IBrandResponseMapper;
import com.bootcamp_2024_2.api_stock.adapters.util.HttpStatusCodes;
import com.bootcamp_2024_2.api_stock.adapters.util.Messages;
import com.bootcamp_2024_2.api_stock.configuration.exceptionhandler.ExceptionResponse;
import com.bootcamp_2024_2.api_stock.domain.api.IBrandServicePort;
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
@RequestMapping("/brand")
@RequiredArgsConstructor
public class BrandRestControllerAdapter {

    private final IBrandServicePort brandServicePort;
    private final IBrandRequestMapper brandRequestMapping;
    private final IBrandResponseMapper brandResponseMapper;

    @Operation(summary = Messages.ADD_NEW_BRAND_SUMMARY)
    @ApiResponses(value = {
            @ApiResponse(responseCode = HttpStatusCodes.CREATED, description = Messages.BRAND_ADDED_SUCCESSFULLY,
                    content = @Content(schema = @Schema(implementation = CreationResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.BAD_REQUEST, description = Messages.INVALID_REQUEST_FORMAT,
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
            @ApiResponse(responseCode = HttpStatusCodes.INTERNAL_SERVER_ERROR, description = Messages.INTERNAL_SERVER_ERROR,
                    content = @Content(schema = @Schema(implementation = ExceptionResponse.class))),
    })
    @PostMapping("/")
    public ResponseEntity<CreationResponse<BrandResponse>> addBrand(@RequestBody @Valid AddBrandRequest brandRequest) {
        BrandResponse brandResponse = brandResponseMapper.toBrandResponse(
                brandServicePort.saveBrand(brandRequestMapping.addRequestToBrand(brandRequest))
        );

        CreationResponse<BrandResponse> response = CreationResponse.<BrandResponse>builder()
                .withMessage(Messages.BRAND_SUCCESSFULLY_RECORDED)
                .withData(brandResponse)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

}
