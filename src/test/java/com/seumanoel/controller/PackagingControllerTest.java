package com.seumanoel.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.seumanoel.config.BaseTestConfig;
import com.seumanoel.config.TestSecurityConfig;
import com.seumanoel.model.*;
import com.seumanoel.security.JwtAuthenticationFilter;
import com.seumanoel.security.JwtTokenProvider;
import com.seumanoel.service.PackagingService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithAnonymousUser;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import java.util.Collections;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestSecurityConfig.class)
@ActiveProfiles("test")
public class PackagingControllerTest extends BaseTestConfig {

    @MockBean
    private JwtTokenProvider jwtTokenProvider;

    @MockBean
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PackagingService packagingService;

    @Test
    @WithMockUser
    public void testCalculatePackaging_Success() throws Exception {
        // Arrange
        Product product = new Product("1", "Smartphone", 15.0, 7.5, 1.0);
        Order order = new Order("1", Collections.singletonList(product));
        PackagingRequest request = new PackagingRequest(Collections.singletonList(order));

        Box box = new Box("1", "Box 1", 30.0, 40.0, 80.0);
        BoxWithProducts boxWithProducts = new BoxWithProducts(box, Collections.singletonList(product), 112.5, 0.0094);
        PackagingResult result = new PackagingResult("1", Collections.singletonList(boxWithProducts));
        PackagingResponse response = new PackagingResponse(Collections.singletonList(result));

        // Configuração do mock
        when(packagingService.processOrders(any(PackagingRequest.class))).thenReturn(response);

        // Act & Assert
        mockMvc.perform(post("/api/packaging")
                        .with(SecurityMockMvcRequestPostProcessors.csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isOk());
    }


    @Test
    @WithAnonymousUser
    public void testCalculatePackaging_Unauthorized() throws Exception {
        // Arrange
        Product product = new Product("1", "Smartphone", 15.0, 7.5, 1.0);
        Order order = new Order("1", Collections.singletonList(product));
        PackagingRequest request = new PackagingRequest(Collections.singletonList(order));

        // Act & Assert - sem autenticação deve falhar
        mockMvc.perform(post("/api/packaging")
                        // Não incluir o SecurityMockMvcRequestPostProcessors.csrf()
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andDo(MockMvcResultHandlers.print())
                .andExpect(status().isUnauthorized());
    }


}
