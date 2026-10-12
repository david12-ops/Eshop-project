package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.model.Category;
import com.example.service_interface.CategoryService;

@RestController 
@RequestMapping("/categories")
public class CategoryController {
        private final CategoryService categoryService;

        public CategoryController(CategoryService categoryService) {
                this.categoryService = categoryService;
        }

        // LIST
        @GetMapping
        public List<Category> listCategories() {
                return categoryService.getCategories();
        }

        // DETAIL
        @GetMapping("/{id}")
        public Category categoryDetail(@PathVariable Integer id) {
                return categoryService.getCategoryById(id);
        }

        // CREATE
        @PostMapping ("/create")
        public Category createCategory(@RequestBody Category category) {
                return categoryService.saveCategory(category);
        }

        // EDIT
        @PutMapping ("/{id}")
        public Category editCategory(
                        @PathVariable Integer id,
                        @RequestBody Category category) {
                return  categoryService.editCategory(id, category);
        }

        // DELETE
        @DeleteMapping ("/{id}")
        public void deleteCategory(@PathVariable Integer id) {
                categoryService.deleteCategoryById(id);
        }
}