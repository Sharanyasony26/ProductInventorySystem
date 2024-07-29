package com.example.springproject.service;

import com.example.springproject.entity.Product;
import com.example.springproject.exception.ProductNotFoundException;

import java.util.List;

public interface ProductService {
    Product saveProduct(Product product);
    List<Product> saveProducts(List<Product> products);
    List<Product> getProducts();
    Product getProductById(Long id) throws ProductNotFoundException;
    String deleteProduct(Long id);
    Product update(Product product) throws ProductNotFoundException;
    List<Product> findProductsByCategory(String category);
    List<Product> findProductsByPriceRange(Double minPrice, Double maxPrice);
}
