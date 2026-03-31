package com.second_round_backend.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.second_round_backend.entity.Product;
import com.second_round_backend.security.JwtUtil;
import com.second_round_backend.service.ProductService;

@RequestMapping("/api")
@RestController
public class ProductController {

	private final ProductService productService;
	private final JwtUtil jwtUtil; 
	
	public ProductController(ProductService productService, JwtUtil jwtUtil) {
		this.productService = productService;
		this.jwtUtil = jwtUtil;
	}
	
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/addProduct")
	public String addProduct(@RequestBody Product product) {
		return productService.addProduct(product);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@PostMapping("/updateProduct")
	public String updateProduct(@RequestBody Product newProduct) {
		return productService.updateProduct(newProduct);
	}
	
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/deleteProduct/{id}")
	public String deleteProduct(@PathVariable Long id) {
		return productService.deleteProduct(id);
	}
	
	@PreAuthorize("hasRole('USER')")
	@PostMapping("/addProductInCart")
	public String addProductInCart(@RequestBody Product product, @RequestParam String token) {
		System.err.println("hellllllll");
	    String	userEmail = jwtUtil.extractEmail(token);
		return productService.addProductInCart(product, userEmail);
	}
	
}
