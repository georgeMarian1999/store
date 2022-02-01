package com.project.repo;

import com.project.model.Order;
import com.project.model.OrderDetail;
import com.project.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {

    List<Order> findAllByUserId(Integer userId);

    List<Order> findAllById(Integer id);

    List<Order> findAllByOrderDetails(OrderDetail orderDetail);
}
