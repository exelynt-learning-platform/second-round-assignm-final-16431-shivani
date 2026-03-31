package com.second_round_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.second_round_backend.entity.Product;
import com.second_round_backend.entity.User;
import com.second_round_backend.repository.ProductRepository;
import com.second_round_backend.repository.UserRepository;

@Service
public class ProductService {
	
	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	
	public ProductService(ProductRepository productRepository, UserRepository userRepository) {
		this.productRepository = productRepository;
		this.userRepository = userRepository;
	}

	public String addProduct(Product product) {
		
		Optional<Product> p1 = productRepository.findByProductname(product.getProductname());
		
		if(p1.isEmpty()) {
			productRepository.save(product);
			return "Product added successfuly";
		}
		else {
			return "Product Already available";
		}
		
	}
	
	public String updateProduct(Product newProduct) {
		Optional<Product> p1 = productRepository.findById(newProduct.getProductId());
		if(p1.isEmpty()) {
			return "Product Not Found";
		}
		
		Product product = p1.get();
		product.setProductname(newProduct.getProductname());
		product.setDescription(newProduct.getDescription());
		product.setPrice(newProduct.getPrice());
		product.setQuantity(newProduct.getQuantity());
		productRepository.save(product);
		
		return "Update Successful";
	}

	public String deleteProduct(Long id) {
		Optional<Product> p1 = productRepository.findById(id);
		if(!p1.isEmpty()) {
			Product product = p1.get();
			productRepository.delete(product);
			return "Product Deleted";
		}
		return "Product Not Found";
	}

	public String addProductInCart(Product product, String userEmail) {
		Optional<User> u1 = userRepository.findByEmail(userEmail);
		Optional<Product> p1 = productRepository.findById(product.getProductId());
		
		if(u1.isEmpty() || p1.isEmpty()) {
			return "Something wents wrong";
		}
		
		User user = u1.get();
		Product OrgProduct = p1.get();
		
		OrgProduct.setUser(user);
	    
		return "Product Add in Cart";
	}

	public List<Product> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products;
	}

}
