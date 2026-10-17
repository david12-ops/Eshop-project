package com.example.controller;

import java.util.List;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.dto.CategoryDTO;
import com.example.model.MyUserDetail;
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
        public List<CategoryDTO> listCategories(@AuthenticationPrincipal MyUserDetail userDetail) {
                return categoryService.getCategories(userDetail);
        }

        // DETAIL
        @GetMapping("/{id}")
        public CategoryDTO categoryDetail(
                        @PathVariable Integer id,
                        @AuthenticationPrincipal MyUserDetail userDetail) {
                return categoryService.getCategoryById(id, userDetail);
        }

        // CREATE
        @PostMapping("/create")
        public CategoryDTO createCategory(
                        @RequestBody CategoryDTO categoryDTO,
                        @AuthenticationPrincipal MyUserDetail userDetail) {
                return categoryService.saveCategory(categoryDTO, userDetail);
        }

        // EDIT
        @PutMapping("/{id}")
        public CategoryDTO editCategory(
                        @PathVariable Integer id,
                        @RequestBody CategoryDTO categoryDTO,
                        @AuthenticationPrincipal MyUserDetail userDetail) {
                return categoryService.editCategory(id, categoryDTO, userDetail);
        }

        // DELETE
        @DeleteMapping("/{id}")
        public void deleteCategory(
                        @PathVariable Integer id,
                        @AuthenticationPrincipal MyUserDetail userDetail) {
                categoryService.deleteCategoryById(id, userDetail);
        }
}