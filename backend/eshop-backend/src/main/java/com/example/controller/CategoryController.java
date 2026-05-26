package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Category;
import com.example.service_interface.CategoryService;

@Controller
@RequestMapping("/categories")
public class CategoryController {
        private final CategoryService categoryService;

        public CategoryController(CategoryService categoryService) {
                this.categoryService = categoryService;
        }

        // LIST
        @GetMapping
        public String listCategories(Model model) {

                model.addAttribute("categories", categoryService.getAllCategories());

                return "categories/list";
        }

        // DETAIL
        @GetMapping("/detail/{id}")
        public String categoryDetail(
                        @PathVariable Integer id,
                        Model model) {

                Category category = categoryService.getCategoryById(id);

                model.addAttribute(
                                "category",
                                category);

                return "categories/detail";
        }

        // CREATE FORM
        @GetMapping("/create")
        public String createForm(Model model) {

                model.addAttribute(
                                "category",
                                new Category());

                return "categories/create";
        }

        // CREATE SAVE
        @PostMapping("/create")
        public String createCategory(
                        @ModelAttribute Category category) {

                categoryService.saveCategory(category);

                return "redirect:/categories";
        }

        // EDIT FORM
        @GetMapping("/edit/{id}")
        public String editForm(
                        @PathVariable Integer id,
                        Model model) {

                Category category = categoryService.getCategoryById(id);

                model.addAttribute(
                                "category",
                                category);

                return "categories/edit";
        }

        // EDIT SAVE
        @PostMapping("/edit/{id}")
        public String editCategory(
                        @PathVariable Integer id,
                        @ModelAttribute Category category) {

                categoryService.editCategory(id, category);

                return "redirect:/categories";
        }

        // DELETE
        @PostMapping("/delete/{id}")
        public String deleteCategory(
                        @PathVariable Integer id) {

                categoryService.deleteCategoryById(id);

                return "redirect:/categories";
        }
}