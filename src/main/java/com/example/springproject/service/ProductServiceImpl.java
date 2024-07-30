package com.example.springproject.service;

import com.example.springproject.dao.ProductDao;
import com.example.springproject.entity.Product;
import com.example.springproject.exception.ProductNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductDao productDao;

    public Product saveProduct(Product product) {
        return productDao.save(product);
    }

public List<Product> saveProducts(List<Product>products){
    return productDao.saveAll(products);
}
    public List<Product> getProducts() {
        return productDao.findAll();
    }
    public Product getProductById(Long id) throws ProductNotFoundException {
        return productDao.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product not available with ID: " + id));
    }


    public String deleteProduct(Long id) {
        productDao.delete(id);
        return "Product removed with ID: " + id;
    }


    public Product update(Product product) throws ProductNotFoundException {
        if (!productDao.findById(product.getId()).isPresent()) {
            throw new ProductNotFoundException("Product not found with ID: " + product.getId());
        }
        return productDao.save(product);
    }


    public List<Product> findProductsByCategory(String category) {
        return productDao.findByCategory(category);
    }


    public List<Product> findProductsByPriceRange(Double minPrice, Double maxPrice) {
        return productDao.findByPriceRange(minPrice, maxPrice);
    }
}

