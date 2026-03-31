package com.second_round_backend.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.second_round_backend.entity.Order;
import com.second_round_backend.service.OrderService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService service;

    @PostMapping("/{userId}")
    public Order create(@PathVariable Long userId) {
        return service.createOrder(userId);
    }

    @GetMapping("/{id}")
    public Order get(@PathVariable Long id) throws Exception {
        return service.getOrder(id);
    }
}