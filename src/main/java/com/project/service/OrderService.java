package com.project.service;

import com.project.model.Order;
import com.project.model.OrderDetail;
import com.project.model.Product;
import com.project.repo.OrderDetailRepository;
import com.project.repo.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    public List<Order> findAllOrdersByUserId(Integer userId) {
        return orderRepository.findAllByUserId(userId);
    }

    public List<Order> findAllOrdersById(Integer id) {
        return orderRepository.findAllById(id);
    }

    public List<Order> findAllOrders() {
        return orderRepository.findAll();
    }

    public List<Order> findAllOrderByOrderDetails(OrderDetail orderDetail) {
        return orderRepository.findAllByOrderDetails(orderDetail);
    }

    public List<OrderDetail> findAllOrdersDetailsByProduct(Product product) {
        return orderDetailRepository.findAllByProduct(product);
    }

    public List<OrderDetail> findAllOrdersDetailsByOrder(Order order) {
        return orderDetailRepository.findAllByOrder(order);
    }
}
