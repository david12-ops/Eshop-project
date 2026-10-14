package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Category;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service_interface.CategoryService;
import com.example.service_interface.ProductService;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
    }

    @Override
    public Product getProductById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product saveProduct(Product product) {
        if (product == null) {
            throw new BadRequestException("Product must not be null");
        }

        if (product.getCategory() != null
                && product.getCategory().getCategoryId() == null) {

            product.setCategory(null);
        }

        return productRepository.save(product);
    }

    // Soft deletion
    @Override
    public void deleteProductById(Integer id) {
        Product existingProduct = getProductById(id);

        // ?
        if (existingProduct.getDeleted())
            return;

        productRepository.softDeleteById(existingProduct.getProductId());
    }

    @Override
    public Product editProduct(Integer id, Product product) {
        if (product == null) {
            throw new BadRequestException("Product to edit must not be null");
        }

        Product existingProduct = getProductById(id);

        existingProduct.setProductName(product.getProductName());
        existingProduct.setProductDescription(product.getProductDescription());
        existingProduct.setRecommendedPrice(product.getRecommendedPrice());
        existingProduct.setActive(product.isActive());

        if (product.getCategory() != null && product.getCategory().getCategoryId() != null) {
            Category category = categoryService.getCategoryById(product.getCategory().getCategoryId());
            existingProduct.setCategory(category);
        } else {

            existingProduct.setCategory(null);
        }

        return productRepository.save(existingProduct);
    }
}
