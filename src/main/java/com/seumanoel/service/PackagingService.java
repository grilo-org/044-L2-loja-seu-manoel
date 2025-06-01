package com.seumanoel.service;

import com.seumanoel.model.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PackagingService {

    // Definition of available boxes according to specification
    private final List<Box> availableBoxes = Arrays.asList(
            new Box("1", "Box 1", 30.0, 40.0, 80.0),
            new Box("2", "Box 2", 80.0, 50.0, 40.0),
            new Box("3", "Box 3", 50.0, 80.0, 60.0)
    );

    public PackagingResponse processOrders(PackagingRequest request) {
        PackagingResponse response = new PackagingResponse();

        for (Order order : request.getOrders()) {
            PackagingResult result = packOrder(order);
            response.getResults().add(result);
        }

        return response;
    }

    private PackagingResult packOrder(Order order) {
        PackagingResult result = new PackagingResult();
        result.setOrderId(order.getId());

        // Sort products by volume (largest to smallest)
        List<Product> sortedProducts = new ArrayList<>(order.getProducts());
        sortedProducts.sort(Comparator.comparing(Product::getVolume).reversed());

        List<Product> remainingProducts = new ArrayList<>(sortedProducts);

        while (!remainingProducts.isEmpty()) {
            // Find the best box for remaining products
            BoxWithProducts bestBox = findBestBox(remainingProducts);
            result.getBoxesUsed().add(bestBox);

            // Remove packed products from remaining list
            remainingProducts.removeAll(bestBox.getProducts());
        }

        return result;
    }

    private BoxWithProducts findBestBox(List<Product> products) {
        // Initially try with the smallest box
        List<Box> sortedBoxes = new ArrayList<>(availableBoxes);
        sortedBoxes.sort(Comparator.comparing(Box::getVolume));

        for (Box box : sortedBoxes) {
            BoxWithProducts boxWithProducts = new BoxWithProducts();
            boxWithProducts.setBox(box);

            // Try to pack as many products as possible in this box
            for (Product product : products) {
                if (boxWithProducts.canFitProduct(product)) {
                    boxWithProducts.addProduct(product);
                }
            }

            // If we managed to pack at least one product, return this box
            if (!boxWithProducts.getProducts().isEmpty()) {
                return boxWithProducts;
            }
        }

        // If no box can fit a product, use the largest box for the first product
        Box largestBox = availableBoxes.stream()
                .max(Comparator.comparing(Box::getVolume))
                .orElseThrow();

        BoxWithProducts boxWithProducts = new BoxWithProducts();
        boxWithProducts.setBox(largestBox);
        boxWithProducts.addProduct(products.get(0));

        return boxWithProducts;
    }
}
