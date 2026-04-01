package com.second_round_backend.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productname;

    private String description;

    @Column(nullable = false)
    private double price;

    private int quantity;
}