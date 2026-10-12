package com.example.service_interface;

import java.util.List;

import com.example.model.Category;

public interface CategoryService {

    List<Category> getCategories();

    Category saveCategory(Category category);

    void deleteCategoryById(Integer id);

    Category editCategory(Integer id, Category category);

    Category getCategoryById(Integer id);
}
