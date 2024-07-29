package com.example.springproject.dao;

import com.example.springproject.entity.Product;
import com.example.springproject.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

class ProductDaoImplTest {
@InjectMocks
    private ProductDaoImpl productDao;
@Mock
    private ProductRepository repository;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }
    @Test
    void testFindById()  {
        Product product = new Product(252L, "electronics", "laptop", 1099.99, 5);

        when(repository.findById(anyLong())).thenReturn(Optional.of(product));

        Optional<Product> foundProduct = productDao.findById(252L);
        assertTrue(foundProduct.isPresent());
        assertEquals(product.getId(), foundProduct.get().getId());
        verify(repository, times(1)).findById(anyLong());
    }
    @Test
    void testSaveProduct() {
        Product product = new Product(252L, "electronics", "laptop", 1099.99, 5);
        when(repository.save(product)).thenReturn(product);

        Product savedProduct = productDao.save(product);
        assertEquals(product.getId(), savedProduct.getId());
        verify(repository, times(1)).save(product);
    }
    @Test
    void testFindByCategory(){
        Product product1 = new Product(252L, "electronics", "laptop", 1099.99, 5);
        Product product2 = new Product(253L, "electronics", "tablet", 499.99, 10);
        List<Product> products= Arrays.asList(product1,product2);
        when(repository.findByCategory(anyString())).thenReturn(products);
        List<Product> foundProductByCategory=productDao.findByCategory("laptop");
        assertNotNull(foundProductByCategory);
        assertEquals(2, foundProductByCategory.size());
        verify(repository, times(1)).findByCategory(anyString());
    }
    @Test
    void testFindProductsByPriceRange() {
        Product product1 = new Product(252L, "electronics", "laptop", 1099.99, 5);
        Product product2 = new Product(253L, "electronics", "tablet", 499.99, 10);
        List<Product> products = Arrays.asList(product1, product2);

        when(repository.findByPriceRange(anyDouble(), anyDouble())).thenReturn(products);

        List<Product> foundProducts = productDao.findByPriceRange(400.00, 1200.00);

        assertNotNull(foundProducts);
        assertEquals(2, foundProducts.size());
        verify(repository, times(1)).findByPriceRange(anyDouble(), anyDouble());
    }


}