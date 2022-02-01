package com.project.controller;

import com.project.model.Category;
import com.project.model.Review;
import com.project.model.dto.ProductDTO;
import com.project.repo.CategoryRepository;
import com.project.service.CategoryService;
import com.project.utils.DTOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "http://localhost:8081")
@RestController
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllCategories() {
        List<Category> categoryList = new ArrayList<>();
        categoryService.findAllCategories()
                .forEach(category -> {
                    if (category != null) {
                        categoryList.add(category);
                    }
                });
        if (categoryList.size() > 0)
            return new ResponseEntity<>(categoryList, HttpStatus.OK);
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
