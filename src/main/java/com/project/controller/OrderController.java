package com.project.controller;


import com.project.model.*;
import com.project.model.dto.OrderDTO;
import com.project.model.dto.ProductDTO;
import com.project.model.dto.ProductOrderDTO;
import com.project.model.enums.OrderStatus;
import com.project.service.OrderService;
import com.project.service.ProductService;
import com.project.service.UserService;
import com.project.utils.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllOrder() {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orderDTOList = buildOrderDtoList(orderService.findAllOrders());
        if (orderDTOList.size() > 0)
            return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/allByUserId/{userId}")
    public ResponseEntity<?> getAllOrderByUserId(@PathVariable("userId") Integer userId) {
        List<OrderDTO> orderDTOList = new ArrayList<>();
        Optional<User> user = userService.findUserById(userId);
        if (user.isPresent())
            orderDTOList = buildOrderDtoList(orderService.findAllOrdersByUserId(user.get()));
        if (orderDTOList.size() > 0)
            return new ResponseEntity<>(orderDTOList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/orderById/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable("orderId") Integer orderId) {
        OrderDTO orderDTO = new OrderDTO();
        Optional<Order> order = orderService.findOrderById(orderId);
        List<ProductOrderDTO> productOrderDTOList = new ArrayList<>();
        order.ifPresent(value ->
                orderService.findAllOrdersDetailsByOrder(value)
                .forEach(orderDetail -> {
                            productService.findAllProductsByOrderDetails(orderDetail)
                                    .forEach(product -> {
                                        productOrderDTOList.add(DTOUtils.productOrderToDto(product, orderDetail));
                                    });
                }));
        orderDTO = DTOUtils.orderToDto(order.get(), productOrderDTOList);
        return new ResponseEntity<>(orderDTO, HttpStatus.OK);
//        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    private List<OrderDTO> buildOrderDtoList(List<Order> orders){
        List<OrderDTO> orderDTOList = new ArrayList<>();
        orders.forEach(order -> {
                    orderService.findAllOrdersDetailsByOrder(order)
                            .forEach(orderDetail -> {
                                List<ProductOrderDTO> productOrderDTOList = new ArrayList<>();
                                productService.findAllProductsByOrderDetails(orderDetail)
                                        .forEach(product -> {
                                            productOrderDTOList.add(DTOUtils.productOrderToDto(product, orderDetail));
                                            orderDTOList.add(DTOUtils.orderToDto(order, productOrderDTOList));
                                        });
                            });
                });
        return orderDTOList;
    }

    @PostMapping("/placeOrder/{userId}")
    public ResponseEntity<?> placeOrder(@PathVariable("userId") Integer userId, @RequestBody OrderDTO orderDTO) {
        Optional<User> user = userService.findUserById(userId);
        Order order = new Order(user.get(), orderDTO.getPhone(), orderDTO.getStreet(), orderDTO.getApartment(), orderDTO.getCity(), orderDTO.getCountry(), orderDTO.getPostcode(), orderDTO.getSubtotal(), orderDTO.getTaxes(), orderDTO.getTotal(), OrderStatus.PROCESSING, LocalDate.now(), orderDTO.getShipping());
        orderService.saveOrder(order);
        List<ProductOrderDTO> productOrderDTOList = orderDTO.getProductOrderDTOList();
        for (ProductOrderDTO p : productOrderDTOList) {
            Optional<Product> product = productService.findProductById(p.getId());
            int stock = product.get().getStock() - p.getQuantity();
            product.get().setStock(stock);
            productService.saveProduct(product.get());
            OrderDetail orderDetail = new OrderDetail(order, product.get(), p.getQuantity());
            orderService.saveOrderDetail(orderDetail);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/shipOrder/{orderId}")
    public ResponseEntity<?> shipOrder(@PathVariable("orderId") Integer orderId) {
        Optional<Order> order = orderService.findOrderById(orderId);
        order.get().setOrderStatus(OrderStatus.DELIVERED);
        orderService.saveOrder(order.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PutMapping("/cancelOrder/{orderId}")
    public ResponseEntity<?> cancelOrder(@PathVariable("orderId") Integer orderId) {
        Optional<Order> order = orderService.findOrderById(orderId);
        order.get().setOrderStatus(OrderStatus.CANCELED);
        List<OrderDetail> ordersDetails = orderService.findAllOrdersDetailsByOrder(order.get());
        for (OrderDetail ordDetail : ordersDetails){
            List<Product> products = productService.findAllProductsByOrderDetails(ordDetail);
            for (Product p : products){
                p.setStock(p.getStock() + ordDetail.getQuantity());
                productService.saveProduct(p);
            }
        }
        orderService.saveOrder(order.get());
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
