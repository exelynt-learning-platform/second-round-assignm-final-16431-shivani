package com.second_round_backend.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.second_round_backend.config.SecurityConfig;
import com.second_round_backend.dto.AuthRequest;
import com.second_round_backend.dto.AuthResponse;
import com.second_round_backend.dto.UserDtoRequest;
import com.second_round_backend.entity.User;
import com.second_round_backend.repository.UserRepository;
import com.second_round_backend.security.JwtUtil;

@Service
public class UserService {
	
	private final UserRepository userRepository;
	private final JwtUtil jwtUtil;
	private final SecurityConfig securityConfig;
	private final AuthResponse authResponse;
	
	
	public UserService(UserRepository userRepository, JwtUtil jwtUtil, SecurityConfig securityConfig, AuthResponse authResponse) {
		this.userRepository = userRepository;
		this.jwtUtil = jwtUtil;
		this.securityConfig = securityConfig;
		this.authResponse = authResponse;
	}

	public String register(UserDtoRequest userDtoRequest) {
		System.out.println(userDtoRequest);
		Optional<User> u1 = userRepository.findByEmail(userDtoRequest.getEmail());
		
		if(u1.isPresent()) {
			return "User Already Exist";
		}
		else {
			User user = new User();
			user.setUsername(userDtoRequest.getUsername());
			user.setEmail(userDtoRequest.getEmail());
			user.setPassword(securityConfig.passwordEncoder().encode(userDtoRequest.getPassword()));
			user.setRole(userDtoRequest.getRole());
			
			userRepository.save(user);
			return "User Register Successful";
		}
		
	}

	public AuthResponse login(AuthRequest authRequest) throws Exception {
		
		Optional<User> u1 = userRepository.findByEmail(authRequest.getEmail());
		
		System.err.println(u1);
		
		if(u1.isEmpty()) {
			throw new Exception("User Not Found");
		}
		
		if(u1.isPresent()) {
			User user = u1.get();
			
			if(securityConfig.passwordEncoder().matches(authRequest.getPassword(), user.getPassword())) {
				System.err.println("true");
				String token = jwtUtil.generateToken(user.getEmail());
			
				authResponse.setToken(token);
				authResponse.setRole(user.getRole());
			}
			else {
				throw new Exception("Invalid Password");
			}
		}
		
		return authResponse;
	}

}
