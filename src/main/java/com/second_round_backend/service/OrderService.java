package com.second_round_backend.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.second_round_backend.entity.Cart;
import com.second_round_backend.entity.CartItem;
import com.second_round_backend.entity.Order;
import com.second_round_backend.entity.OrderItem;
import com.second_round_backend.repository.CartRepository;
import com.second_round_backend.repository.OrderRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final CartRepository cartRepo;
    private final OrderRepository orderRepo;

    public Order createOrder(Long userId) {

        Cart cart = cartRepo.findByUserId(userId);

        if (cart == null) {
            throw new RuntimeException("Cart not found for user");
        }

        if (cart.getCartItems() == null || cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setUser(cart.getUser());

        List<OrderItem> orderItems = new ArrayList<>();

        double total = 0;

        for (CartItem item : cart.getCartItems()) {

            OrderItem oi = new OrderItem();
            oi.setProduct(item.getProduct());
            oi.setQuantity(item.getQuantity());


            oi.setOrder(order);

            total += item.getProduct().getPrice() * item.getQuantity();

            orderItems.add(oi);
        }

        order.setOrderitems(orderItems);
        order.setTotalPrice(total);
        order.setStatus("PENDING");

        Order savedOrder = orderRepo.save(order);

        cart.getCartItems().clear();
        cartRepo.save(cart);

        return savedOrder;
    }

    public Order getOrder(Long id) throws Exception {
        return orderRepo.findById(id)
                .orElseThrow(() ->
                        new Exception("Order not found with id: " + id));
    }
}