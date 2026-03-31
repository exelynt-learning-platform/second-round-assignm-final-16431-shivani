package com.second_round_backend.service;

import java.util.Iterator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.second_round_backend.entity.Cart;
import com.second_round_backend.entity.CartItem;
import com.second_round_backend.entity.Product;
import com.second_round_backend.entity.User;
import com.second_round_backend.repository.CartRepository;
import com.second_round_backend.repository.ProductRepository;
import com.second_round_backend.repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepo;
    private final ProductRepository productRepo;
    private final UserRepository userRepository;
    

    public Cart addToCart(Long userId, Long productId, int qty) {

        Cart cart = cartRepo.findByUserId(userId);

        Product product = productRepo.findById(productId)
                .orElseThrow();

        CartItem item = new CartItem();
        item.setProduct(product);
        item.setQuantity(qty);
        item.setCart(cart);

        cart.getCartItems().add(item);

        return cartRepo.save(cart);
    }


	public String removeFromCart(Long userId, Long productId) {
		
		Cart cart = cartRepo.findByUserId(userId);
		
		List<CartItem> items = cart.getCartItems();
		
		Iterator<CartItem> list = items.iterator();
		while(list.hasNext()) {
			CartItem item = list.next();
			if(item.getProduct().getProductId() == productId) {
				cartRepo.deleteById(item.getId());
			}
		}
		
		return "Product remove successfully";
	}

	public List<CartItem> getCartItems(Long userId) {
		User user = userRepository.findById(userId).orElseThrow();
		
		return user.getCart().getCartItems();
	}
}