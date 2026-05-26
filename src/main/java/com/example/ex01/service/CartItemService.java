package com.example.ex01.service;

import com.example.ex01.model.dto.CartItemRequest;
import com.example.ex01.model.entity.CartItem;
import com.example.ex01.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemService {

    private final CartItemRepository repository;

    public CartItem addCartItem(CartItemRequest request) {
        log.info("Processing add to cart - User: {}, Product: {}, Quantity: {}",
                request.getUserId(), request.getProductId(), request.getQuantity());

        CartItem item = repository.findByUserIdAndProductId(request.getUserId(), request.getProductId())
                .map(existingItem -> {
                    existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
                    log.info("Product {} already exists for user {}. Updating quantity to: {}",
                            request.getProductId(), request.getUserId(), existingItem.getQuantity());
                    return existingItem;
                }).orElseGet(() -> {
                    log.info("Product {} is new for user {}. Creating new cart entry.",
                            request.getProductId(), request.getUserId());
                    return CartItem.builder()
                            .userId(request.getUserId())
                            .productId(request.getProductId())
                            .quantity(request.getQuantity())
                            .build();
                });

        CartItem savedItem = repository.save(item);
        log.info("Successfully saved cart item with ID: {}", savedItem.getId());
        return savedItem;
    }

    public List<CartItem> getCartItemsByUserId(String userId) {
        log.info("Fetching cart items for User: {}", userId);
        return repository.findByUserId(userId);
    }
}