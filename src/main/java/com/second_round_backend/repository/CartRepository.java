package com.second_round_backend.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.second_round_backend.entity.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
	Cart findByUserId(Long userId);

}
