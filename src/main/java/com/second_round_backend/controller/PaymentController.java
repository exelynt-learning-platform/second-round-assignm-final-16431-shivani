package com.second_round_backend.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.second_round_backend.service.PaymentService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/create/{orderId}")
    public String createPayment(@PathVariable Long orderId) throws Exception {
        return paymentService.createPayment(orderId);
    }

    @PostMapping("/confirm")
    public String confirmPayment(@RequestParam String paymentIntentId) throws Exception {
        return paymentService.confirmPayment(paymentIntentId);
    }
}