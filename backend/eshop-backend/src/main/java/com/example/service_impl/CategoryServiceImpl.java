package com.example.service_impl;

import java.time.Instant;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.dto.CategoryDTO;
import com.example.exception.BadRequestException;
import com.example.model.Category;
import com.example.model.MyUserDetail;
import com.example.repository.CategoryRepository;
import com.example.service_interface.CategoryService;
import com.example.utils.utils_interface.ObjectMapper;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    private final ObjectMapper<Category, CategoryDTO> categoryMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
            ObjectMapper<Category, CategoryDTO> categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public CategoryDTO getCategoryById(Integer id, MyUserDetail userDetail) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return categoryRepository.findById(id).map(category -> categoryMapper.mapToDTO(category))
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    public List<CategoryDTO> getCategories(MyUserDetail userDetail) {
        return categoryRepository.findAll().stream()
                .map(categoryMapper::mapToDTO).toList();
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO, MyUserDetail userDetail) {
        if (categoryDTO == null) {
            throw new BadRequestException("Category must not be null");
        }

        categoryDTO.setCreatedAt(Instant.now());
        categoryDTO.setUpdatedAt(Instant.now());

        categoryDTO.setCreatedBy(userDetail.getUserId());
        categoryDTO.setUpdatedBy(userDetail.getUserId());

        categoryDTO.setActive(false);
        categoryDTO.setDeleted(false);
        categoryDTO.setDeletedAt(null);

        Category savedCategory = categoryRepository.save(categoryMapper.mapToEntity(categoryDTO));

        return categoryMapper.mapToDTO(savedCategory);
    }

    // Soft deletion
    @Override
    public void deleteCategoryById(Integer id, MyUserDetail userDetail) {
        Category categoryToDelete = getCategoryEntityById(id);

        if (categoryToDelete.isDeleted())
            return;

        categoryToDelete.setUpdatedAt(Instant.now());
        categoryToDelete.setUpdatedBy(userDetail.getUserId());

        categoryToDelete.setActive(false);
        categoryToDelete.setDeleted(true);
        categoryToDelete.setDeletedAt(Instant.now());

        categoryRepository.save(categoryToDelete);
        // categoryRepository.softDeleteById(existingCategory.getCategoryId());
    }

    @Override
    public CategoryDTO editCategory(Integer id, CategoryDTO categoryDTO, MyUserDetail userDetail) {
        if (categoryDTO == null) {
            throw new BadRequestException("Category to edit must not be null");
        }

        Category categoryToUpdate = getCategoryEntityById(id);

        categoryToUpdate.setCategoryName(categoryDTO.getCategoryName());
        categoryToUpdate.setCategoryDescription(categoryDTO.getCategoryDescription());
        categoryToUpdate.setUpdatedAt(Instant.now());
        categoryToUpdate.setUpdatedBy(userDetail.getUserId());
        categoryToUpdate.setDeleted(false);
        categoryToUpdate.setDeletedAt(null);

        return categoryMapper.mapToDTO(categoryRepository.save(categoryToUpdate));
    }

    private Category getCategoryEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }
}
