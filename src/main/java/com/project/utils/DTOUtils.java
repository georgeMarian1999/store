package com.project.utils;

import com.project.model.*;
import com.project.model.dto.OrderDTO;
import com.project.model.dto.ProductDTO;
import com.project.model.dto.ProductOrderDTO;
import com.project.model.dto.UserDTO;

import java.util.List;

public class DTOUtils {
    public static UserDTO userToDto(User user) {
        UserDTO udto = new UserDTO();
        udto.setEmail(user.getEmail());
        udto.setFirstName(user.getName());
        udto.setUsername(user.getUsername());
        udto.setPassword(user.getPassword());
        if (user.getRole() != null) {
            udto.setRole(user.getRole()
                    .getName());
        }
        return udto;
    }

    public static ProductDTO productToDto(Product product, List<Review> reviews) {
        ProductDTO productDTO = new ProductDTO();
        productDTO.setId(product.getId());
        productDTO.setName(product.getName());
        productDTO.setDescription(product.getDescription());
        productDTO.setImage(product.getImage());
        productDTO.setNewProduct(product.getNewProduct());
        productDTO.setPrice(product.getPrice());
        productDTO.setStock(product.getStock());
        productDTO.setAddedDate(product.getAddedDate());
        productDTO.setSale(product.getSale());
        productDTO.setCategory(product.getCategory());
        double stars = reviews.stream()
                .mapToInt(Review::getStars)
                .average()
                .orElse(Double.NaN);
        productDTO.setStars((int)stars);
        productDTO.setReviews(reviews);
        return productDTO;
    }

    public static ProductOrderDTO productOrderToDto(Product product, OrderDetail orderDetail) {
        ProductOrderDTO productOrderDTO = new ProductOrderDTO();
        productOrderDTO.setId(product.getId());
        productOrderDTO.setName(product.getName());
        productOrderDTO.setPrice(product.getPrice());
        productOrderDTO.setQuantity(orderDetail.getQuantity());
        return productOrderDTO;
    }

    public static OrderDTO orderToDto(Order order, List<ProductOrderDTO> productOrderDTOList) {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setId(order.getId());
        orderDTO.setPhone(order.getPhone());
        orderDTO.setStreet(order.getStreet());
        orderDTO.setApartment(order.getApartment());
        orderDTO.setCity(order.getCity());
        orderDTO.setCountry(order.getCountry());
        orderDTO.setPostcode(order.getPostcode());
        orderDTO.setSubtotal(order.getSubtotal());
        orderDTO.setTaxes(order.getTaxes());
        orderDTO.setTotal(order.getTotal());
        orderDTO.setOrderStatus(order.getOrderStatus());
        orderDTO.setDate(order.getDate());
        orderDTO.setShipping(order.getShipping());
        orderDTO.setProductOrderDTOList(productOrderDTOList);
        return orderDTO;
    }

}
