package com.second_round_backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.second_round_backend.entity.Cart;
import com.second_round_backend.entity.CartItem;
import com.second_round_backend.entity.Product;
import com.second_round_backend.entity.User;
import com.second_round_backend.repository.CartRepository;
import com.second_round_backend.repository.ProductRepository;
import com.second_round_backend.repository.UserRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {
	
	private final ProductRepository productRepository;
	private final UserRepository userRepository;
	private final CartRepository cartRepository;
	
	

	public String addProduct(Product product) {

	    if (product.getPrice() <= 0) {
	        return "Price must be greater than 0";
	    }

	    if (product.getQuantity() < 0) {
	        return "Quantity cannot be negative";
	    }

	    Optional<Product> p1 = productRepository
	            .findByProductname(product.getProductname());

	    if (p1.isPresent()) {
	        return "Product already available";
	    }

	    productRepository.save(product);
	    return "Product added successfully";
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

	public Cart addToCart(Long userId, Long productId, int qty) {

	    User user = userRepository.findById(userId)
	            .orElseThrow(() -> new RuntimeException("User not found"));

	    Cart cart = cartRepository.findByUserId(userId);

	    if (cart == null) {
	        cart = new Cart();
	        cart.setUser(user);
	    }

	    Product product = productRepository.findById(productId)
	            .orElseThrow(() -> new RuntimeException("Product not found"));

	    if (product.getQuantity() < qty) {
	        throw new RuntimeException("Insufficient stock available");
	    }

	    Optional<CartItem> existingItem = cart.getCartItems()
	            .stream()
	            .filter(i -> i.getProduct().getProductId().equals(productId))
	            .findFirst();

	    if (existingItem.isPresent()) {

	        CartItem item = existingItem.get();

	        int newQty = item.getQuantity() + qty;

	        if (product.getQuantity() < newQty) {
	            throw new RuntimeException("Not enough stock for requested quantity");
	        }

	        item.setQuantity(newQty);

	    } else {

	        CartItem item = new CartItem();
	        item.setProduct(product);
	        item.setQuantity(qty);
	        item.setCart(cart);

	        cart.getCartItems().add(item);
	    }

	    return cartRepository.save(cart);
	}

	public List<Product> getAllProducts() {
		List<Product> products = productRepository.findAll();
		return products;
	}

}
