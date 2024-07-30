package com.example.springproject.controller;

import com.example.springproject.entity.Product;
import com.example.springproject.exception.ProductNotFoundException;
import com.example.springproject.service.ProductServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ProductControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private ProductController productController;

    @Mock
    private ProductServiceImpl productService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(productController).build();
    }

    @Test
    public void testAddProduct() throws Exception {
        Product product = new Product(252L, "electronics", "laptop", 1099.99, 5);

        when(productService.saveProduct(any(Product.class))).thenReturn(product);

        mockMvc.perform(post("/addProduct")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":252,\"name\":\"electronics\",\"category\":\"laptop\",\"price\":1099.99,\"quantity\":5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("electronics"))
                .andExpect(jsonPath("$.price").value(1099.99))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    public void testAddProducts() throws Exception {
        Product product1 = new Product(252L, "electronics", "laptop", 1099.99, 5);
        Product product2 = new Product(253L, "electronics", "tablet", 499.99, 10);

        List<Product> products = Arrays.asList(product1, product2);
        when(productService.saveProducts(any(List.class))).thenReturn(products);

        mockMvc.perform(post("/addProducts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("[{\"id\":252,\"name\":\"electronics\",\"category\":\"laptop\",\"price\":1099.99,\"quantity\":5}," +
                                "{\"id\":2,\"name\":\"electronics\",\"category\":\"tablet\",\"price\":499.99,\"quantity\":10}]"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("electronics"))
                .andExpect(jsonPath("$[1].name").value("electronics"));
    }

    @Test
    public void testFindAllProducts() throws Exception {
        Product product1 = new Product(252L, "electronics", "laptop", 1099.99, 5);
        Product product2 = new Product(253L, "electronics", "tablet", 499.99, 10);

        List<Product> products = Arrays.asList(product1, product2);
        when(productService.getProducts()).thenReturn(products);

        mockMvc.perform(get("/Products"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("electronics"))
                .andExpect(jsonPath("$[1].name").value("electronics"));
    }

    @Test
    public void testFindProductById() throws Exception {
        Product product = new Product(252L, "electronics", "laptop", 1099.99, 5);

        when(productService.getProductById(anyLong())).thenReturn(product);

        mockMvc.perform(get("/ProductById/252"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("electronics"))
                .andExpect(jsonPath("$.price").value(1099.99))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    public void testDeleteProduct() throws Exception {
        when(productService.deleteProduct(anyLong())).thenReturn("Product deleted successfully");

        mockMvc.perform(delete("/deleteById/252"))
                .andExpect(status().isOk())
                .andExpect(content().string("Product deleted successfully"));
    }

    @Test
    public void testUpdateProduct() throws Exception {
        Product product = new Product(252L, "electronics", "laptop", 1099.99, 5);

        when(productService.update(any(Product.class))).thenReturn(product);

        mockMvc.perform(put("/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"id\":252,\"name\":\"electronics\",\"category\":\"laptop\",\"price\":1099.99,\"quantity\":5}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("electronics"))
                .andExpect(jsonPath("$.price").value(1099.99))
                .andExpect(jsonPath("$.quantity").value(5));
    }

    @Test
    public void testGetProductsByCategory() throws Exception {
        Product product = new Product(252L, "electronics", "laptop", 1099.99, 5);

        List<Product> products = Arrays.asList(product);
        when(productService.findProductsByCategory(anyString())).thenReturn(products);

        mockMvc.perform(get("/category/laptop"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("electronics"));
    }

    @Test
    public void testGetProductsByPriceRange() throws Exception {
        Product product1 = new Product(252L, "electronics", "laptop", 1099.99, 5);
        Product product2 = new Product(253L, "electronics", "tablet", 499.99, 10);

        List<Product> products = Arrays.asList(product1, product2);
        when(productService.findProductsByPriceRange(anyDouble(), anyDouble())).thenReturn(products);

        mockMvc.perform(get("/price-range")
                        .param("minPrice", "1000.0")
                        .param("maxPrice", "2000.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("electronics"))
                .andExpect(jsonPath("$[1].name").value("electronics"));
    }
}
