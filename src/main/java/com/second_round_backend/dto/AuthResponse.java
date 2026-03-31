package com.second_round_backend.dto;

import org.springframework.stereotype.Component;

import com.second_round_backend.entity.Role;

import lombok.Data;

@Component
@Data
public class AuthResponse {

	private String token;
	private Role role;
}
