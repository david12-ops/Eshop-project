package com.example.utils;

import org.springframework.stereotype.Component;

import com.example.dto.CategoryDTO;
import com.example.dto.ProductDTO;
import com.example.exception.BadRequestException;
import com.example.model.Product;
import com.example.model.Category;
import com.example.utils.utils_interface.ObjectMapper;

@Component
public class ProductMapperImpl implements ObjectMapper<Product, ProductDTO> {
    private final ObjectMapper<Category, CategoryDTO> categoryMapper;

    public ProductMapperImpl(ObjectMapper<Category, CategoryDTO> categoryMapper) {
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Product mapToEntity(ProductDTO objectDTO) {
        if ((objectDTO.getProductName() == null || objectDTO.getProductName().isBlank())
                || (objectDTO.getProductCode() == null || objectDTO.getProductCode().isBlank())
                || (objectDTO.getProductImageUrl() == null || objectDTO.getProductImageUrl().isBlank())
                || objectDTO.getRecommendedPrice() == null
                || objectDTO.getUnitPrice() == null
                || objectDTO.getTaxRate() == null
                || objectDTO.isActive() == null
                || objectDTO.isDeleted() == null) {
            throw new BadRequestException(
                    "Name, code, image, recommended price, unit price, tax rate, activation and deletion state are reqired");
        }

        Product product = new Product();

        product.setProductName(objectDTO.getProductName());
        product.setProductCode(objectDTO.getProductCode());
        product.setProductDescription(objectDTO.getProductDescription());
        product.setProductImageUrl(objectDTO.getProductImageUrl());
        product.setRecommendedPrice(objectDTO.getRecommendedPrice());
        product.setUnitPrice(objectDTO.getUnitPrice());
        product.setTaxRate(objectDTO.getTaxRate());
        product.setCategory(categoryMapper.mapToEntity(objectDTO.getCategoryDTO()));
        product.setActive(objectDTO.isActive());
        product.setDeleted(objectDTO.isDeleted());
        product.setDeletedAt(objectDTO.getDeletedAt());
        product.setCreatedAt(objectDTO.getCreatedAt());
        product.setUpdatedAt(objectDTO.getUpdatedAt());
        product.setCreatedBy(objectDTO.getCreatedBy());
        product.setUpdatedBy(objectDTO.getUpdatedBy());

        return product;
    }

    @Override
    public ProductDTO mapToDTO(Product objectEntity) {
        ProductDTO productDTO = new ProductDTO();

        productDTO.setProductId(objectEntity.getProductId());
        productDTO.setProductName(objectEntity.getProductName());
        productDTO.setProductCode(objectEntity.getProductCode());
        productDTO.setProductDescription(objectEntity.getProductDescription());
        productDTO.setProductImageUrl(objectEntity.getProductImageUrl());
        productDTO.setRecommendedPrice(objectEntity.getRecommendedPrice());
        productDTO.setUnitPrice(objectEntity.getUnitPrice());
        productDTO.setTaxRate(objectEntity.getTaxRate());
        productDTO.setCategoryDTO(categoryMapper.mapToDTO(objectEntity.getCategory()));
        productDTO.setActive(objectEntity.isActive());
        productDTO.setDeleted(objectEntity.isDeleted());
        productDTO.setDeletedAt(objectEntity.getDeletedAt());
        productDTO.setCreatedAt(objectEntity.getCreatedAt());
        productDTO.setUpdatedAt(objectEntity.getUpdatedAt());
        productDTO.setCreatedBy(objectEntity.getCreatedBy());
        productDTO.setUpdatedBy(objectEntity.getUpdatedBy());

        return productDTO;
    }
}
