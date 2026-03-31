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

        Order order = new Order();
        order.setUser(cart.getUser());

        List<OrderItem> orderItems = new ArrayList();

        double total = 0;

        for (CartItem item : cart.getCartItems()) {

            OrderItem oi = new OrderItem();
            oi.setProduct(item.getProduct());
            oi.setQuantity(item.getQuantity());

            total += item.getProduct().getPrice() * item.getQuantity();

            orderItems.add(oi);
        }

        order.setOrderitems(orderItems);
        order.setTotalPrice(total);
        order.setStatus("PENDING");

        return orderRepo.save(order);
    }

	public Order getOrder(Long id) throws Exception {
		return orderRepo.findById(id)
	            .orElseThrow(() ->
	                    new Exception("Order not found with id: " + id));
	}
}