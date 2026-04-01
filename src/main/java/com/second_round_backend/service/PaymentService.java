package com.second_round_backend.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.second_round_backend.entity.Order;
import com.second_round_backend.repository.OrderRepository;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {

    @Value("${stripe.secret.key}")
    private String apiKey;

    private final OrderRepository orderRepository;

    public String createPayment(Long orderId) throws StripeException {

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        Stripe.apiKey = apiKey;

        Map<String, Object> params = new HashMap<>();
        params.put("amount", (int) (order.getTotalPrice() * 100));
        params.put("currency", "inr");

        PaymentIntent intent = PaymentIntent.create(params);

        order.setPaymentId(intent.getId());
        order.setPaymentStatus("PENDING");

        orderRepository.save(order);

        return intent.getClientSecret();
    }
    
    public String confirmPayment(String paymentIntentId) {

        Order order = orderRepository.findAll()
                .stream()
                .filter(o -> paymentIntentId.equals(o.getPaymentId()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Order not found"));

        order.setPaymentStatus("SUCCESS");
        order.setStatus("CONFIRMED");

        orderRepository.save(order);

        return "Payment successful, order confirmed!";
    }
}