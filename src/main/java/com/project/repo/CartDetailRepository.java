package com.project.repo;

import com.project.model.Cart;
import com.project.model.CartDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartDetailRepository extends JpaRepository<CartDetail, Integer> {

    List<CartDetail> findAllByCart(Cart cart);
}
