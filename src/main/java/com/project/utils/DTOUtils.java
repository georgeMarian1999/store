package com.project.utils;

import com.project.model.Category;
import com.project.model.Product;
import com.project.model.Review;
import com.project.model.User;
import com.project.model.dto.ProductDTO;
import com.project.model.dto.UserDTO;

import java.util.List;

public class DTOUtils {
    public static UserDTO userToDto(User user) {
        UserDTO udto = new UserDTO();
        udto.setEmail(user.getEmail());
        udto.setFirstName(user.getFirstName());
        udto.setLastName(user.getLastName());
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
        productDTO.setStars(stars);
        productDTO.setReviews(reviews);
        return productDTO;
    }
}
