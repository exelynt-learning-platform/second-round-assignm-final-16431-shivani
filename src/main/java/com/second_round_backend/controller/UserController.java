package com.second_round_backend.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.second_round_backend.dto.AuthRequest;
import com.second_round_backend.dto.AuthResponse;
import com.second_round_backend.dto.UserDtoRequest;
import com.second_round_backend.service.UserService;

@RestController
@RequestMapping("/api/auth")
public class UserController {

	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public String register(@RequestBody UserDtoRequest userDtoRequest) {
		return userService.register(userDtoRequest);
	}
	
	@PostMapping("/login")
	public AuthResponse login(@RequestBody AuthRequest authRequest) throws Exception {
		return userService.login(authRequest);
	}
}
