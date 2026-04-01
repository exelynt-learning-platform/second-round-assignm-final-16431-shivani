package com.second_round_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.second_round_backend.entity.Cart;
import com.second_round_backend.entity.CartItem;
import com.second_round_backend.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @PostMapping("/items")
    public Cart addItem(@RequestParam Long userId,
                        @RequestParam Long productId,
                        @RequestParam int qty) {

        return service.addToCart(userId, productId, qty);
    }


    @DeleteMapping("/items")
    public String removeItem(@RequestParam Long userId,
                             @RequestParam Long productId) {

        return service.removeFromCart(userId, productId);
    }

    @GetMapping("/items/{userId}")
    public List<CartItem> getCartItems(@PathVariable Long userId) {
        return service.getCartItems(userId);
    }
}