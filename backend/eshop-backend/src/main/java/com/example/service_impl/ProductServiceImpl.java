package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.ProductCardDto;
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

        if (product.getCategory() != null
                && product.getCategory().getId() == null) {

            product.setCategory(null);
        }

        productRepository.save(product);
    }

    @Override
    public void deleteProductById(Integer id) {
        if (id == null)
            return;

        // zatím ne
        productRepository.findById(id).ifPresent(product -> {
            if (product.getDeleted())
                return; // tady jen soft kvůli klíčům

            productRepository.softDeleteById(id);
        });
    }

    @Override
    public void editProduct(Integer id, Product product) {
        if (id == null || product == null)
            return;

        productRepository.findById(id).ifPresent(existingProduct -> {
            existingProduct.setProductName(product.getProductName());
            existingProduct.setProductDescription(product.getProductDescription());
            existingProduct.setRecommendedPrice(product.getRecommendedPrice());
            existingProduct.setActive(product.isActive());

            if (product.getCategory() != null
                    && product.getCategory().getId() != null) {

                Category category = categoryService.getCategoryById(
                        product.getCategory().getId());

                existingProduct.setCategory(category);

            } else {

                existingProduct.setCategory(null);
            }

            productRepository.save(existingProduct);
        });
    }

    @Override
    public List<ProductCardDto> getFeaturedProducts() {
        return productRepository.findTop12ByOrderByCreatedAtDesc()
                .stream().filter(pr -> !pr.getDeleted())
                .map(product -> new ProductCardDto(
                        product.getId(),
                        product.getProductName(),
                        product.getProductDescription(),
                        product.getRecommendedPrice(),
                        product.getCategory().getCategoryName(),
                        product.getProductImageUrl()))
                .toList();
    }
}
