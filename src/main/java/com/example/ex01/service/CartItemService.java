package com.example.ex01.service;

import com.example.ex01.model.dto.CartItemRequest;
import com.example.ex01.model.entity.CartItem;
import com.example.ex01.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class CartItemService {

    private final CartItemRepository repository;

    // LƯUỒNG CHÍNH: Cực kỳ trong sáng, đọc vào hiểu ngay đang làm gì
    public CartItem addCartItem(CartItemRequest request) {
        log.info("Processing add to cart - User: {}, Product: {}, Quantity: {}",
                request.getUserId(), request.getProductId(), request.getQuantity());

        Optional<CartItem> existingItemOpt = repository.findByUserIdAndProductId(
                request.getUserId(), request.getProductId()
        );

        CartItem itemToSave;

        if (existingItemOpt.isPresent()) {
            itemToSave = updateExistingItem(existingItemOpt.get(), request.getQuantity());
        } else {
            itemToSave = createNewItem(request);
        }

        CartItem savedItem = repository.save(itemToSave);
        log.info("Successfully saved cart item with ID: {}", savedItem.getId());

        return savedItem;
    }

    public List<CartItem> getCartItemsByUserId(String userId) {
        log.info("Fetching cart items for User: {}", userId);
        return repository.findByUserId(userId);
    }

    // --- CÁC HÀM PHỤ TRỢ (HELPER METHODS) ---

    private CartItem updateExistingItem(CartItem existingItem, Integer addedQuantity) {
        existingItem.setQuantity(existingItem.getQuantity() + addedQuantity);

        log.info("Product {} already exists for user {}. Updating quantity to: {}",
                existingItem.getProductId(), existingItem.getUserId(), existingItem.getQuantity());

        return existingItem;
    }

    private CartItem createNewItem(CartItemRequest request) {
        log.info("Product {} is new for user {}. Creating new cart entry.",
                request.getProductId(), request.getUserId());

        return CartItem.builder()
                .userId(request.getUserId())
                .productId(request.getProductId())
                .quantity(request.getQuantity())
                .build();
    }
}