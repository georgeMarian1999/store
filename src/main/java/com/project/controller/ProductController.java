package com.project.controller;


import com.project.model.Category;
import com.project.model.Product;
import com.project.model.Review;
import com.project.model.dto.ProductDTO;
import com.project.service.CategoryService;
import com.project.service.ProductService;
import com.project.service.ReviewService;
import com.project.utils.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ReviewService reviewService;

    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        productService.findAllProducts()
                .forEach(product -> {
                    List<Review> reviewsList = reviewService.findAllReviewsByProduct(Optional.ofNullable(product));
                    if (product != null) {
                        productDTOList.add(DTOUtils.productToDto(product, reviewsList));
                    }
                });
        if (productDTOList.size() > 0)
            return new ResponseEntity<>(productDTOList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/allOnSale")
    public ResponseEntity<?> getAllProductsOnSale() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        productService.findAllProductOnSale(0)
                .forEach(product -> {
                    List<Review> reviewsList = reviewService.findAllReviewsByProduct(Optional.ofNullable(product));
                    if (product != null) {
                        productDTOList.add(DTOUtils.productToDto(product, reviewsList));
                    }
                });
        if (productDTOList.size() > 0)
            return new ResponseEntity<>(productDTOList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/allNew")
    public ResponseEntity<?> getAllNewProducts() {
        List<ProductDTO> productDTOList = new ArrayList<>();
        productService.findAllNewProducts()
                .forEach(product -> {
                    List<Review> reviewsList = reviewService.findAllReviewsByProduct(Optional.ofNullable(product));
                    if (product != null) {
                        productDTOList.add(DTOUtils.productToDto(product, reviewsList));
                    }
                });
        if (productDTOList.size() > 0)
            return new ResponseEntity<>(productDTOList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/viewProduct/{productId}")
    public ResponseEntity<?> getProductById(@PathVariable("productId") Integer productId) {
        ProductDTO productDTO = new ProductDTO();
        Optional<Product> product = productService.findProductById(productId);
        if (product.isPresent()) {
            List<Review> reviews = reviewService.findAllReviewsByProduct(product);
            productDTO = DTOUtils.productToDto(product.get(), reviews);
            return new ResponseEntity<>(productDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/allByCategory/{categoryId}")
    public ResponseEntity<?> getAllProductsByCategory(@PathVariable("categoryId") Integer categoryId) {
        List<ProductDTO> productDTOList = new ArrayList<>();
        Optional<Category> category = categoryService.findCategoryById(categoryId);
        category.ifPresent(value ->
                productService.findProductsByCategory(value)
                        .forEach(product -> {
                            List<Review> reviewsList = reviewService.findAllReviewsByProduct(Optional.ofNullable(product));
                            if (product != null) {
                            productDTOList.add(DTOUtils.productToDto(product, reviewsList));
                            }
                        }));
        if (productDTOList.size() > 0)
            return new ResponseEntity<>(productDTOList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
