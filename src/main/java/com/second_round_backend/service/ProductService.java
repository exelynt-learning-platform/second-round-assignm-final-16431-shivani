package com.second_round_backend.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.second_round_backend.entity.Product;
import com.second_round_backend.exceptionHandler.BadRequestException;
import com.second_round_backend.exceptionHandler.ResourceNotFoundException;
import com.second_round_backend.repository.ProductRepository;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public Product addProduct(Product product) {

        if (product.getPrice() <= 0) {
            throw new BadRequestException("Price must be greater than 0");
        }

        if (product.getQuantity() < 0) {
            throw new BadRequestException("Quantity cannot be negative");
        }

        productRepository.findByProductname(product.getProductname())
                .ifPresent(p -> {
                    throw new BadRequestException("Product already exists");
                });

        return productRepository.save(product);
    }

    public Product updateProduct(Product newProduct) {

        Product product = productRepository.findById(newProduct.getProductId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        if (newProduct.getPrice() <= 0) {
            throw new BadRequestException("Invalid price");
        }

        if (newProduct.getQuantity() < 0) {
            throw new BadRequestException("Invalid quantity");
        }

        product.setProductname(newProduct.getProductname());
        product.setDescription(newProduct.getDescription());
        product.setPrice(newProduct.getPrice());
        product.setQuantity(newProduct.getQuantity());

        return productRepository.save(product);
    }

    public void deleteProduct(Long id) {

        Product product = productRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Product not found"));

        productRepository.delete(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }
}