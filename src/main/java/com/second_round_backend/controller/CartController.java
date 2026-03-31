package com.second_round_backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.second_round_backend.entity.Cart;
import com.second_round_backend.entity.CartItem;
import com.second_round_backend.service.CartService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService service;

    @PostMapping("/addProductInCart")
    public Cart add(@RequestParam Long userId,
                    @RequestParam Long productId,
                    @RequestParam int qty) {

        return service.addToCart(userId, productId, qty);
    }

    @DeleteMapping("/removeProductFromCart")
    public String remove(@RequestParam Long userId,
                       @RequestParam Long productId) {

        return service.removeFromCart(userId, productId);
    }

    @GetMapping("/getCartItems/{userId}")
    public List<CartItem> getCart(@PathVariable Long userId) {
        return service.getCartItems(userId);
    }
}