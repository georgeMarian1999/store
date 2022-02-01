package com.project.service;

import com.project.model.Category;
import com.project.model.OrderDetail;
import com.project.model.Product;
import com.project.repo.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAllProducts() {
        return productRepository.findAll();
    }

    public List<Product> findAllProductOnSale(Integer saleValue) {
        return productRepository.findAllBySaleIsGreaterThan(saleValue);
    }

    public List<Product> findAllNewProducts() {
        return productRepository.findAllByNewProductIsTrue();
    }

    public Optional<Product> findProductById(Integer productId) {
        return productRepository.findById(productId);
    }

    public List<Product> findProductsByCategory(Category category) {
        return productRepository.findAllByCategory(category);
    }

    public List<Product> findAllProductsByOrderDetails(OrderDetail orderDetail) {
        return productRepository.findAllByOrderDetails(orderDetail);
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }
}
