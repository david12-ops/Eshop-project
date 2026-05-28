package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.exception.ResourceNotFoundException;
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
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public void saveCategory(Category category) {
        if (category == null)
            return;

        categoryRepository.save(category);
    }

    @Override
    public void deleteCategoryById(Integer id) {
        if (id == null)
            return;

        // zatím ne
        categoryRepository.findById(id).ifPresent(category -> {
            if (category.getDeleted())
                return; // tady jen soft kvůli klíčům

            categoryRepository.softDeleteById(id);
        });
    }

    @Override
    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Category not found with id: " + id));
    }

    @Override
    public void editCategory(Integer id, Category category) {
        if (id == null || category == null)
            return;

        categoryRepository.findById(id).ifPresent(existingCategory -> {

            existingCategory.setCategoryName(category.getCategoryName());
            existingCategory.setCategoryDescription(category.getCategoryDescription());
            existingCategory.setActive(category.isActive());

            categoryRepository.save(existingCategory);
        });
    }
}
