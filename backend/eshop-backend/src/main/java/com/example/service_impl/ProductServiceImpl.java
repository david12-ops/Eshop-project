package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service_interface.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product getProductById(Integer id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public void saveProduct(Product product) {
        if (product == null)
            return;

        productRepository.save(product);
    }

    @Override
    public void deleteProductById(Integer id) {
        if (id == null)
            return;

        productRepository.findById(id).ifPresent(product -> productRepository.deleteById(id));
    }

    @Override
    public void editProduct(Integer id, Product product) {
        if (id == null || product == null)
            return;

        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found"));

        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductDescription(product.getProductDescription());
        existingProduct.setRecommendedPrice(product.getRecommendedPrice());
        existingProduct.setActive(product.isActive());

        productRepository.save(existingProduct);
    }
}
