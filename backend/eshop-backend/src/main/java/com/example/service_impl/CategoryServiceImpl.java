package com.example.service_impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

        categoryRepository.findById(id).ifPresent(category -> categoryRepository.deleteById(id));
    }

    @Override
    @Transactional
    public Category getCategoryWithItems(Integer id) {

        return categoryRepository.findByIdWithItems(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public Category getCategoryById(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    @Override
    public void editCategory(Integer id, Category category) {
        if (id == null || category == null)
            return;

        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));

        existingCategory.setCategoryName(category.getCategoryName());
        existingCategory.setCategoryDescription(category.getCategoryDescription());
        existingCategory.setActive(category.isActive());

        categoryRepository.save(existingCategory);
    }
}
