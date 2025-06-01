package com.seumanoel.controller;

import com.seumanoel.model.PackagingRequest;
import com.seumanoel.model.PackagingResponse;
import com.seumanoel.service.PackagingService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/packaging")
@RequiredArgsConstructor
@Tag(name = "Packaging", description = "API for optimized packaging calculation")
public class PackagingController {

    private final PackagingService packagingService;

    @PostMapping
    @Operation(
            summary = "Calculate order packaging",
            description = "Calculates the best way to pack products from each order into available boxes"
    )
    public ResponseEntity<PackagingResponse> calculatePackaging(
            @Valid @RequestBody PackagingRequest request) {
        PackagingResponse response = packagingService.processOrders(request);
        return ResponseEntity
                .<PackagingResponse>ok()
                .contentType(MediaType.APPLICATION_JSON)
                .body(response);
    }

}
