package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
import com.example.exception.BadRequestException;
import com.example.model.Category;
import com.example.repository.CategoryRepository;
import com.example.service_interface.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public Category saveCategory(Category category) {
        if (category == null) {
            throw new BadRequestException("Category must not be null");
        }

        return categoryRepository.save(category);
    }

    // Soft deletion
    @Override
    public void deleteCategoryById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        categoryRepository.findById(id).ifPresent(category -> {
            if (category.getDeleted())
                return;

            categoryRepository.softDeleteById(id);
        });
    }

    @Override
    public Category getCategoryById(Integer id) {
        if (id == null) {
            throw new BadRequestException("ID must not be null");
        }

        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    public Category editCategory(Integer id, Category category) {
        if (id == null || category == null) {
            throw new BadRequestException("ID or category to edit must not be null");
        }

        return categoryRepository.findById(id).map(existingCategory -> {
            existingCategory.setCategoryName(category.getCategoryName());
            existingCategory.setCategoryDescription(category.getCategoryDescription());
            existingCategory.setActive(category.isActive());

            return categoryRepository.save(existingCategory);
        }).orElse(null);
    }
}
