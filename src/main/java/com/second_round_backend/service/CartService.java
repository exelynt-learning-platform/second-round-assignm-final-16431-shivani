package com.second_round_backend.service;

import java.util.Iterator;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.second_round_backend.entity.Cart;
import com.second_round_backend.entity.CartItem;
import com.second_round_backend.entity.Product;
import com.second_round_backend.entity.User;
import com.second_round_backend.repository.CartRepository;
import com.second_round_backend.repository.ProductRepository;
import com.second_round_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepository;
    

    public Cart addToCart(Long userId, Long productId, int qty) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Cart cart = cartRepo.findByUserId(userId);

        if (cart == null) {
            cart = new Cart();
            cart.setUser(user);
        }

        Product product = productRepo.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Optional<CartItem> existingItem = cart.getCartItems()
                .stream()
                .filter(i -> i.getProduct().getProductId().equals(productId))
                .findFirst();

        if (existingItem.isPresent()) {

            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + qty);
        } else {
  
            CartItem item = new CartItem();
            item.setProduct(product);
            item.setQuantity(qty);
            item.setCart(cart);

            cart.getCartItems().add(item);
        }

        return cartRepo.save(cart);
    }

    public String removeFromCart(Long userId, Long productId) {

        Cart cart = cartRepo.findByUserId(userId);

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        List<CartItem> items = cart.getCartItems();

        boolean removed = items.removeIf(item ->
                item.getProduct().getProductId().equals(productId)
        );

        if (!removed) {
            throw new RuntimeException("Product not found in cart");
        }

        cartRepo.save(cart);

        return "Product removed successfully";
    }

    public List<CartItem> getCartItems(Long userId) {

        Cart cart = cartRepo.findByUserId(userId);

        if (cart == null) {
            throw new RuntimeException("Cart not found");
        }

        return cart.getCartItems();
    }
}