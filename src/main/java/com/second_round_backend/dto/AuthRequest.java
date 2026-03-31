package com.second_round_backend.dto;

import org.springframework.stereotype.Component;

import lombok.Data;

@Component
@Data
public class AuthRequest {

	private String email;
	private String password;
}
