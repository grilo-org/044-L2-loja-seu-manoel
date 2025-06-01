package com.seumanoel.service;

import com.seumanoel.model.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class PackagingServiceTest {

    @InjectMocks
    private PackagingService packagingService;

    @Test
    public void testProcessOrders_SingleProduct() {
        // Arrange
        Product smartphone = new Product("1", "Smartphone", 15.0, 7.5, 1.0);
        Order order = new Order("1", Collections.singletonList(smartphone));
        PackagingRequest request = new PackagingRequest(Collections.singletonList(order));

        // Act
        PackagingResponse response = packagingService.processOrders(request);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getResults().size());

        PackagingResult result = response.getResults().get(0);
        assertEquals("1", result.getOrderId());
        assertEquals(1, result.getBoxesUsed().size());

        BoxWithProducts boxWithProducts = result.getBoxesUsed().get(0);
        assertEquals("1", boxWithProducts.getBox().getId()); // Deve usar a menor caixa possível
        assertEquals(1, boxWithProducts.getProducts().size());
    }

    @Test
    public void testProcessOrders_MultipleProducts() {
        // Arrange
        Product smartphone = new Product("1", "Smartphone", 15.0, 7.5, 1.0);
        Product laptop = new Product("2", "Laptop", 2.5, 35.0, 25.0);
        Product headphones = new Product("3", "Headphones", 10.0, 10.0, 5.0);

        List<Product> products = Arrays.asList(smartphone, laptop, headphones);
        Order order = new Order("1", products);
        PackagingRequest request = new PackagingRequest(Collections.singletonList(order));

        // Act
        PackagingResponse response = packagingService.processOrders(request);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getResults().size());

        PackagingResult result = response.getResults().get(0);
        assertEquals("1", result.getOrderId());

        // Verifica se todos os produtos foram empacotados
        int totalProductsPacked = result.getBoxesUsed().stream()
                .mapToInt(box -> box.getProducts().size())
                .sum();
        assertEquals(3, totalProductsPacked);
    }

    @Test
    public void testProcessOrders_MultipleOrders() {
        // Arrange
        Product smartphone = new Product("1", "Smartphone", 15.0, 7.5, 1.0);
        Order order1 = new Order("1", Collections.singletonList(smartphone));

        Product laptop = new Product("2", "Laptop", 2.5, 35.0, 25.0);
        Order order2 = new Order("2", Collections.singletonList(laptop));

        PackagingRequest request = new PackagingRequest(Arrays.asList(order1, order2));

        // Act
        PackagingResponse response = packagingService.processOrders(request);

        // Assert
        assertNotNull(response);
        assertEquals(2, response.getResults().size());
        assertEquals("1", response.getResults().get(0).getOrderId());
        assertEquals("2", response.getResults().get(1).getOrderId());
    }

    @Test
    public void testProcessOrders_LargeProduct() {
        // Arrange - produto maior que qualquer caixa disponível
        Product largeTV = new Product("4", "Large TV", 100.0, 150.0, 20.0);
        Order order = new Order("1", Collections.singletonList(largeTV));
        PackagingRequest request = new PackagingRequest(Collections.singletonList(order));

        // Act
        PackagingResponse response = packagingService.processOrders(request);

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getResults().size());

        PackagingResult result = response.getResults().get(0);
        assertEquals("1", result.getOrderId());
        assertEquals(1, result.getBoxesUsed().size());

        // Deve usar a maior caixa disponível
        BoxWithProducts boxWithProducts = result.getBoxesUsed().get(0);
        assertEquals("3", boxWithProducts.getBox().getId()); // Box 3 é a maior
    }
}
