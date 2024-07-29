package com.example.springproject.dao;

import com.example.springproject.entity.Product;

import java.util.List;
import java.util.Optional;

public interface ProductDao {
    Optional<Product> findById(Long id);
    List<Product> findAll();
    Product save(Product product);
    void delete(Long id);
    List<Product> findByCategory(String category);
    List<Product> findByPriceRange(Double minPrice, Double maxPrice);
}
