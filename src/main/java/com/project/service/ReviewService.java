package com.project.service;

import com.project.model.Product;
import com.project.model.Review;
import com.project.repo.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {
    @Autowired
    private ReviewRepository reviewRepository;

    public List<Review> findAllReviews() {
        return reviewRepository.findAll();
    }

    public List<Review> findAllReviewsByProduct(Optional<Product> product) {
        return reviewRepository.findAllByProduct(product);
    }
}
