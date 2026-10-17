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
        return categoryRepository.findAll().stream().map(category -> categoryMapper.mapToDTO(category)).toList();
    }

    @Override
    public CategoryDTO saveCategory(CategoryDTO categoryDTO, MyUserDetail userDetail) {
        if (categoryDTO == null) {
            throw new BadRequestException("Category must not be null");
        }

        categoryDTO.setActive(false);
        categoryDTO.setDeleted(false);
        categoryDTO.setDeletedAt(null);

        if (categoryDTO.getCreatedAt() == null) {
            categoryDTO.setCreatedAt(Instant.now());
        }

        if (categoryDTO.getUpdatedAt() == null) {
            categoryDTO.setUpdatedAt(Instant.now());
        }

        if (userDetail != null) {
            if (categoryDTO.getCreatedBy() == null) {
                categoryDTO.setCreatedBy(userDetail.getUserId());
            }

            if (categoryDTO.getUpdatedBy() == null) {
                categoryDTO.setUpdatedBy(userDetail.getUserId());
            }
        }

        Category newCategory = categoryMapper.mapToEntity(categoryDTO);

        Category savedCategory = categoryRepository.save(newCategory);

        return categoryMapper.mapToDTO(savedCategory);
    }

    // Soft deletion
    @Override
    public void deleteCategoryById(Integer id, MyUserDetail userDetail) {
        Category existingCategory = getCategoryEntityById(id);

        if (existingCategory.isDeleted())
            return;

        categoryRepository.softDeleteById(existingCategory.getCategoryId());
    }

    @Override
    public CategoryDTO editCategory(Integer id, CategoryDTO categoryDTO, MyUserDetail userDetail) {
        if (categoryDTO == null) {
            throw new BadRequestException("Category to edit must not be null");
        }

        Category categoryToUpdate = getCategoryEntityById(id);

        categoryToUpdate.setCategoryName(categoryDTO.getCategoryName());
        categoryToUpdate.setCategoryDescription(categoryDTO.getCategoryDescription());
        categoryToUpdate.setDeleted(false);
        categoryToUpdate.setDeletedAt(null);

        Category updatedCategory = categoryRepository.save(categoryToUpdate);

        return categoryMapper.mapToDTO(updatedCategory);
    }

    private Category getCategoryEntityById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }
}
