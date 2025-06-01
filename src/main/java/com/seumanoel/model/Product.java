package com.seumanoel.model;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String id;

    @NotBlank(message = "Product name is required")
    private String name;

    @NotNull(message = "Product height is required")
    @Positive(message = "Height must be positive")
    private Double height;

    @NotNull(message = "Product width is required")
    @Positive(message = "Width must be positive")
    private Double width;

    @NotNull(message = "Product length is required")
    @Positive(message = "Length must be positive")
    private Double length;

    public Double getVolume() {
        return height * width * length;
    }
}
