package com.project.controller;


import com.project.model.*;
import com.project.model.dto.OrderDTO;
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
        List<ProductOrderDTO> productOrderDTOList = new ArrayList<>();
        orders.forEach(order -> {
                    orderService.findAllOrdersDetailsByOrder(order)
                            .forEach(orderDetail -> {
                                productService.findAllProductsByOrderDetails(orderDetail)
                                        .forEach(product -> {
                                            productOrderDTOList.add(DTOUtils.productOrderToDto(product, orderDetail));
                                        });

                            });
            orderDTOList.add(DTOUtils.orderToDto(order, productOrderDTOList));
                });
        return orderDTOList;
    }

    @PostMapping("/placeOrder/{userId}")
    public ResponseEntity<?> placeOrder(@PathVariable("userId") Integer userId, @RequestBody OrderDTO orderDTO) {
        Optional<User> user = userService.findUserById(userId);
        List<ProductOrderDTO> productOrderDTOList = orderDTO.getProductOrderDTOList();
        double subtotal = 0;
        int shipping = 0;
        double taxes = 0;
        double total = 0;
        for (ProductOrderDTO p : productOrderDTOList) {
            Optional<Product> product = productService.findProductById(p.getId());
            int stock = product.get().getStock() - p.getQuantity();
            subtotal = subtotal + calcSubtotal(product.get(), p.getQuantity());
            product.get().setStock(stock);
            productService.saveProduct(product.get());
        }
        shipping = calcShipping(subtotal);
        taxes = calcTaxes(subtotal);
        total = calcTotal(subtotal, taxes, shipping);
        Order order = new Order(user.get(), orderDTO.getPhone(), orderDTO.getStreet(), orderDTO.getApartment(), orderDTO.getCity(), orderDTO.getCounty(), orderDTO.getPostcode(), (float) subtotal, (float) taxes, (float) total, OrderStatus.PROCESSING, LocalDate.now(), (float) shipping);
        orderService.saveOrder(order);
        for (ProductOrderDTO p : productOrderDTOList) {
            Optional<Product> product = productService.findProductById(p.getId());
            OrderDetail orderDetail = new OrderDetail(order, product.get(), p.getQuantity());
            orderService.saveOrderDetail(orderDetail);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }

    public Float calcSubtotal(Product product, int quantity){
        return (product.getPrice() - (product.getPrice() * product.getSale()/100) * quantity);
    }

    public int calcShipping(double subtotal){
        return subtotal >= 100 ? 0 : 15;
    }

    public double calcTaxes(double subtotal){
        return (subtotal * 19) / 100;
    }

    public double calcTotal(double subtotal, double taxes, int shippping){
        return subtotal + shippping + taxes;
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
