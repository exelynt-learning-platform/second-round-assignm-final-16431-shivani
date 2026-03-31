package com.second_round_backend.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.Data;


@Entity
@Data
public class Order {

    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private User user;

    private double totalPrice;
    private String status;

    private String shippingAddress;  
    private String paymentStatus;  
    
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItem> orderitems;
    
    public Order() {
        this.orderitems = new ArrayList<>();
    }
}