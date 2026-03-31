package com.second_round_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.second_round_backend.entity.Order;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

}
