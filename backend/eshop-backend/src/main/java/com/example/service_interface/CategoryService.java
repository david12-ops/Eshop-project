package com.example.service_interface;

import java.util.List;

import com.example.model.Category;

public interface CategoryService {

    List<Category> getAllCategories();

    void saveCategory(Category category);

    void deleteCategoryById(Integer id);

    void editCategory(Integer id, Category category);

    Category getCategoryById(Integer id);
}
