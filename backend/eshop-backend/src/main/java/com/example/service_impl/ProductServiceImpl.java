package com.example.service_impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.dto.CategoryDTO;
import com.example.dto.ProductDTO;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.model.Category;
import com.example.model.MyUserDetail;
import com.example.model.Product;
import com.example.repository.ProductRepository;
import com.example.service_interface.CategoryService;
import com.example.service_interface.ProductService;
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final CategoryService categoryService;

    private ObjectMapper<Product, ProductDTO> productMapper;
    private ObjectMapper<Category, CategoryDTO> categoryMapper;

    public ProductServiceImpl(ProductRepository productRepository, CategoryService categoryService,
            ObjectMapper<Product, ProductDTO> productMapper, ObjectMapper<Category, CategoryDTO> categoryMapper) {
        this.productRepository = productRepository;
        this.categoryService = categoryService;
        this.productMapper = productMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public ProductDTO getProductById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return productRepository.findById(id).map(product -> productMapper.mapToDTO(product))
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }

    @Override
    public List<ProductDTO> getProducts(MyUserDetail userDetail) {
        return productRepository.findAll().stream().map(product -> productMapper.mapToDTO(product)).toList();
    }

    @Override
    public ProductDTO saveProduct(ProductDTO productDTO, MyUserDetail userDetail) {
        if (productDTO == null) {
            throw new BadRequestException("Product must not be null");
        }

        productDTO.setActive(false);
        productDTO.setDeleted(false);
        productDTO.setDeletedAt(null);

        if (productDTO.getCreatedAt() == null) {
            productDTO.setCreatedAt(Instant.now());
        }

        if (productDTO.getUpdatedAt() == null) {
            productDTO.setUpdatedAt(Instant.now());
        }

        if (userDetail != null) {
            if (productDTO.getCreatedBy() == null) {
                productDTO.setCreatedBy(userDetail.getUserId());
            }

            if (productDTO.getUpdatedBy() == null) {
                productDTO.setUpdatedBy(userDetail.getUserId());
            }
        }

        Product newProduct = productMapper.mapToEntity(productDTO);

        Product savedProduct = productRepository.save(newProduct);

        return productMapper.mapToDTO(savedProduct);
    }

    // Soft deletion
    @Override
    public void deleteProductById(Integer id, MyUserDetail userDetail) {
        Product existingProduct = getProductEntityById(id);

        if (existingProduct.isDeleted())
            return;

        productRepository.softDeleteById(existingProduct.getProductId());
    }

    @Override
    public ProductDTO editProduct(Integer id, ProductDTO productDTO, MyUserDetail userDetail) {
        if (productDTO == null) {
            throw new BadRequestException("Product to edit must not be null");
        }

        Product productToUpdate = getProductEntityById(id);

        productToUpdate.setProductName(productDTO.getProductName());
        productToUpdate.setProductDescription(productDTO.getProductDescription());
        productToUpdate.setRecommendedPrice(productDTO.getRecommendedPrice());
        productToUpdate.setDeleted(false);
        productToUpdate.setDeletedAt(null);

        if (productDTO.getCategoryDTO() != null && productDTO.getCategoryDTO().getCategoryId() != null) {
            Category category = categoryMapper
                    .mapToEntity(categoryService.getCategoryById(productDTO.getCategoryDTO().getCategoryId(),
                            userDetail));
            productToUpdate.setCategory(category);
        } else {

            productToUpdate.setCategory(null);
        }

        if (productDTO.getUpdatedAt() == null) {
            productDTO.setUpdatedAt(Instant.now());
        }

        if (userDetail != null) {
            if (productDTO.getCreatedBy() == null) {
                productDTO.setCreatedBy(userDetail.getUserId());
            }

            if (productDTO.getUpdatedBy() == null) {
                productDTO.setUpdatedBy(userDetail.getUserId());
            }
        }

        Product updatedProduct = productRepository.save(productToUpdate);

        return productMapper.mapToDTO(updatedProduct);
    }

    private Product getProductEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with id: " + id));
    }
}
