package com.seumanoel.model;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PackagingRequest {

    @NotEmpty(message = "Request must contain at least one order")
    @Valid
    private List<Order> orders;
}