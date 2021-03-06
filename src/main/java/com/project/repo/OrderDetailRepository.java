package com.project.repo;

import com.project.model.Order;
import com.project.model.OrderDetail;
import com.project.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Integer> {

    List<OrderDetail> findAllByOrder(Order order);

    List<OrderDetail> findAllByProduct(Product product);
}
