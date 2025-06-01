package com.seumanoel.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/test")
@Tag(name = "Teste", description = "Endpoints for API testing")
public class TestController {

    @Operation(summary = "Test API", description = "Checks if the API is working")
    @GetMapping
    public ResponseEntity<String> test() {
        return ResponseEntity.ok("Hello from Seu Manoel's Store API!");
    }
}
