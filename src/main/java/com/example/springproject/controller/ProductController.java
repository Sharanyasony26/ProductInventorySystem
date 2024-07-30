package com.example.springproject.controller;

import com.example.springproject.entity.Product;
import com.example.springproject.exception.ProductNotFoundException;
import com.example.springproject.service.ProductServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {
    @Autowired
    private ProductServiceImpl service;

    @PostMapping("/addProduct")
    public Product addProduct(@RequestBody Product product) {
        return service.saveProduct(product);
    }

    @PostMapping("/addProducts")
    public List<Product> addProducts(@RequestBody List<Product> products) {
        return service.saveProducts(products);
    }
        @GetMapping("/Products")
        public List<Product> findAllProducts() {
            return service.getProducts();
        }

        @GetMapping("/ProductById/{id}")
        public Product findProductById(@PathVariable Long id) throws ProductNotFoundException {
            return service.getProductById(id);
        }
       // @GetMapping("/ProductByName/{")
    @DeleteMapping("/deleteById/{id}")
    public String deleteProduct(@PathVariable Long id) {
        return service.deleteProduct(id);
    }
    @PutMapping("/update")
    public Product updateProduct(@RequestBody Product product) throws ProductNotFoundException {
        return service.update(product);
    }
    @GetMapping("/category/{category}")
    public List<Product> getProductsByCategory(@PathVariable String category) {
        return service.findProductsByCategory(category);
    }
    @GetMapping("/price-range")
    public List<Product> getProductsByPriceRange(@RequestParam Double minPrice, @RequestParam Double maxPrice) {
        return service.findProductsByPriceRange(minPrice, maxPrice);
    }

    }
