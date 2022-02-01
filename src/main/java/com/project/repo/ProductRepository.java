package com.project.repo;

import com.project.model.Category;
import com.project.model.OrderDetail;
import com.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Integer> {
    List<Product> findAllBySaleIsGreaterThan(Integer saleValue);

    List<Product> findAllByNewProductIsTrue();

    List<Product> findAllByCategory(Category category);

    List<Product> findAllByOrderDetails(OrderDetail orderDetail);
}
