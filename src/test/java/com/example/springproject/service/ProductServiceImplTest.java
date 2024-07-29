package com.example.springproject.service;

import com.example.springproject.dao.ProductDao;
import com.example.springproject.entity.Product;
import com.example.springproject.exception.ProductNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductDao productDao;
    @BeforeEach
    void setUp() {
       MockitoAnnotations.openMocks(this);
    }
    @Test
    void testSaveProduct() {
        Product product = new Product(252L, "electronics", "laptop", 1099.99, 5);
        when(productDao.save(product)).thenReturn(product);

        Product savedProduct = productService.saveProduct(product);
        assertEquals(product.getId(), savedProduct.getId());
        verify(productDao, times(1)).save(product);
    }
    @Test
    void testSaveProducts() {
        Product product1 = new Product(252L, "electronics", "laptop", 1099.99, 5);
        Product product2 = new Product(253L, "electronics", "tablet", 499.99, 10);
        List<Product> products = Arrays.asList(product1, product2);

        when(productDao.save(any(Product.class))).thenReturn(product1).thenReturn(product2);

        List<Product> savedProducts = productService.saveProducts(products);
        assertEquals(2, savedProducts.size());
        verify(productDao, times(2)).save(any(Product.class));
    }
    @Test
    void testGetProducts() {
        Product product1 = new Product(252L, "electronics", "laptop", 1099.99, 5);
        Product product2 = new Product(253L, "electronics", "tablet", 499.99, 10);
        List<Product> products = Arrays.asList(product1, product2);

        when(productDao.findAll()).thenReturn(products);

        List<Product> Productsfound = productService.getProducts();

        assertEquals(2, Productsfound.size());
        verify(productDao, times(1)).findAll();
    }
    @Test
    void testGetProductById() throws ProductNotFoundException {
        Product product = new Product(252L, "electronics", "laptop", 1099.99, 5);

        when(productDao.findById(anyLong())).thenReturn(Optional.of(product));

        Product foundProduct = productService.getProductById(252L);

        assertNotNull(foundProduct);
        assertEquals(product.getId(), foundProduct.getId());
        verify(productDao, times(1)).findById(anyLong());
    }
    @Test
    void testDeleteProduct() {
        doNothing().when(productDao).delete(anyLong());
        String response = productService.deleteProduct(252L);
        assertEquals("Product removed with ID: 252", response);
        verify(productDao, times(1)).delete(anyLong());
    }

    @Test
    void testUpdateProduct() throws ProductNotFoundException {
        Product existingProduct = new Product(252L, "electronics", "laptop", 1099.99, 5);
        Product updatedProduct = new Product(252L, "electronics", "laptop", 1199.99, 5);

        when(productDao.findById(anyLong())).thenReturn(Optional.of(existingProduct));
        when(productDao.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = productService.update(updatedProduct);

        assertNotNull(result);
        assertEquals(updatedProduct.getId(), result.getId());
        assertEquals(1199.99, result.getPrice());
        verify(productDao, times(1)).findById(anyLong());
        verify(productDao, times(1)).save(any(Product.class));
    }
    @Test
    void testProductsByCategory(){
        Product product1 = new Product(252L, "electronics", "laptop", 1099.99, 5);
        Product product2 = new Product(253L, "electronics", "tablet", 499.99, 10);
        List<Product>products=Arrays.asList(product1,product2);
        when(productDao.findByCategory(anyString())).thenReturn(products);
        List<Product> foundProductByCategory=productService.findProductsByCategory("laptop");
        assertNotNull(foundProductByCategory);
        assertEquals(2, foundProductByCategory.size());
        verify(productDao, times(1)).findByCategory(anyString());
    }
    @Test
    void testFindProductsByPriceRange() {
        Product product1 = new Product(252L, "electronics", "laptop", 1099.99, 5);
        Product product2 = new Product(253L, "electronics", "tablet", 499.99, 10);
        List<Product> products = Arrays.asList(product1, product2);

        when(productDao.findByPriceRange(anyDouble(), anyDouble())).thenReturn(products);

        List<Product> foundProducts = productService.findProductsByPriceRange(400.00, 1200.00);

        assertNotNull(foundProducts);
        assertEquals(2, foundProducts.size());
        verify(productDao, times(1)).findByPriceRange(anyDouble(), anyDouble());
    }

}