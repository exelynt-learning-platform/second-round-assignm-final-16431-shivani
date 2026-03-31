package com.second_round_backend.service;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;

@Service
public class PaymentService {

    @Value("${stripe.secret.key}")
    private String apiKey;

    public String createPayment(Double amount) throws StripeException{

        Stripe.apiKey = apiKey;

        Map<String, Object> params = new HashMap();
        params.put("amount", (int)(amount * 100));
        params.put("currency", "inr");

        PaymentIntent intent = PaymentIntent.create(params);

        return intent.getClientSecret();
    }
}