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
public class Order {

    private String id;

    @NotEmpty(message = "O pedido deve conter pelo menos um produto")
    @Valid
    private List<Product> products;
}