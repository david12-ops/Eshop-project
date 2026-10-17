package com.example.utils;

import org.springframework.stereotype.Component;

import com.example.dto.CategoryDTO;
import com.example.dto.ProductDTO;
import com.example.exception.BadRequestException;
import com.example.model.Category;
import com.example.model.Product;
import com.example.utils.utils_interface.ObjectMapper;

@Component
public class CategoryMapperImpl implements ObjectMapper<Category, CategoryDTO> {
    private final ObjectMapper<Product, ProductDTO> productMapper;

    public CategoryMapperImpl(ObjectMapper<Product, ProductDTO> productMapper) {
        this.productMapper = productMapper;
    }

    @Override
    public Category mapToEntity(CategoryDTO objectDTO) {
        if ((objectDTO.getCategoryName() == null || objectDTO.getCategoryName().isBlank())
                || objectDTO.isActive() == null
                || objectDTO.isDeleted() == null) {
            throw new BadRequestException("Name, activation and deletion state are reqired");
        }

        Category category = new Category();

        category.setCategoryName(objectDTO.getCategoryName());
        category.setCategoryDescription(objectDTO.getCategoryDescription());
        category.setActive(objectDTO.isActive());
        category.setDeleted(objectDTO.isDeleted());
        category.setDeletedAt(objectDTO.getDeletedAt());
        category.setCreatedAt(objectDTO.getCreatedAt());
        category.setUpdatedAt(objectDTO.getUpdatedAt());
        category.setCreatedBy(objectDTO.getCreatedBy());
        category.setUpdatedBy(objectDTO.getUpdatedBy());
        category.setItems(objectDTO.getItemsDTO().stream().map(product -> productMapper.mapToEntity(product)).toList());

        return category;
    }

    @Override
    public CategoryDTO mapToDTO(Category objectEntity) {
        CategoryDTO categoryDTO = new CategoryDTO();

        categoryDTO.setCategoryId(objectEntity.getCategoryId());
        categoryDTO.setCategoryName(objectEntity.getCategoryName());
        categoryDTO.setCategoryDescription(objectEntity.getCategoryDescription());
        categoryDTO.setActive(objectEntity.isActive());
        categoryDTO.setDeleted(objectEntity.isDeleted());
        categoryDTO.setDeletedAt(objectEntity.getDeletedAt());
        categoryDTO.setCreatedAt(objectEntity.getCreatedAt());
        categoryDTO.setUpdatedAt(objectEntity.getUpdatedAt());
        categoryDTO.setCreatedBy(objectEntity.getCreatedBy());
        categoryDTO.setUpdatedBy(objectEntity.getUpdatedBy());
        categoryDTO
                .setItemsDTO(objectEntity.getItems().stream().map(product -> productMapper.mapToDTO(product)).toList());

        return categoryDTO;
    }
}
