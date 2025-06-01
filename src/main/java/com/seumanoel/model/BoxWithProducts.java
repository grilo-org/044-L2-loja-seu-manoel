package com.seumanoel.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BoxWithProducts {

    private Box box;
    private List<Product> products = new ArrayList<>();
    private Double volumeOccupied = 0.0;
    private Double efficiency = 0.0;

    public void addProduct(Product product) {
        products.add(product);
        volumeOccupied += product.getVolume();
        calculateEfficiency();
    }

    public void calculateEfficiency() {
        efficiency = (volumeOccupied / box.getVolume()) * 100;
    }

    public boolean canFitProduct(Product product) {
        return (volumeOccupied + product.getVolume()) <= box.getVolume();
    }
}