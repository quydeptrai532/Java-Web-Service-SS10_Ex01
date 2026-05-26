package com.example.ex01.controller;

import com.example.ex01.model.dto.CartItemRequest;
import com.example.ex01.model.entity.CartItem;
import com.example.ex01.service.CartItemService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
@Slf4j
public class CartController {
    private final CartItemService service;
    @PostMapping("/add")
    public ResponseEntity<CartItem> addCartItem(@Valid @RequestBody CartItemRequest request) {
        log.info("Received POST /api/cart/add request from user: {}", request.getUserId());
        CartItem savedItem=service.addCartItem(request);
        return ResponseEntity.ok(savedItem);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<List<CartItem>> findByUserId(@PathVariable String userId) {
        log.info("Received GET /api/cart/findByUserId request from user: {}", userId);
        return ResponseEntity.ok(service.getCartItemsByUserId(userId));
    }
}
