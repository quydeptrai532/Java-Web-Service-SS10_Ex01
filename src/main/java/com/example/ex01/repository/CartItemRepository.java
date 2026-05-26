package com.example.ex01.repository;

import com.example.ex01.model.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    Optional<CartItem> findByUserIdAndProductId(String userId, String productId);
    List<CartItem> findByUserId(String userId);
}
