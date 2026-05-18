package com.example.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Category;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Controller
@RequestMapping("/categories")
@Transactional
public class CategoryController {

    @PersistenceContext
    private EntityManager entityManager;

    // LIST
    @GetMapping
    public String listCategories(Model model) {

        List<Category> categories = entityManager
                .createQuery(
                        "SELECT c FROM Category c",
                        Category.class)
                .getResultList();

        model.addAttribute(
                "categories",
                categories);

        return "categories/list";
    }

    // DETAIL
    @GetMapping("/detail/{id}")
    public String categoryDetail(
            @PathVariable Integer id,
            Model model) {

        Category category = entityManager
                .createQuery(
                        "SELECT c FROM Category c WHERE c.id = :id",
                        Category.class)
                .setParameter("id", id)
                .getSingleResult();

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

        entityManager
                .createNativeQuery("""
                            INSERT INTO categories
                            (
                                category_name,
                                category_description,
                                is_active,
                                created_at,
                                updated_at,
                                created_by,
                                updated_by
                            )
                            VALUES
                            (
                                :name,
                                :description,
                                :active,
                                NOW(),
                                NOW(),
                                :createdBy,
                                :updatedBy
                            )
                        """)
                .setParameter(
                        "name",
                        category.getCategoryName())
                .setParameter(
                        "description",
                        category.getCategoryDescription())
                .setParameter(
                        "active",
                        category.isActive())
                .setParameter(
                        "createdBy",
                        category.getCreatedBy())
                .setParameter(
                        "updatedBy",
                        category.getUpdatedBy())
                .executeUpdate();

        return "redirect:/categories";
    }

    // EDIT FORM
    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Integer id,
            Model model) {

        Category category = entityManager
                .createQuery(
                        "SELECT c FROM Category c WHERE c.id = :id",
                        Category.class)
                .setParameter("id", id)
                .getSingleResult();

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

        entityManager
                .createNativeQuery("""
                            UPDATE categories
                            SET
                                category_name = :name,
                                category_description = :description,
                                is_active = :active,
                                updated_at = NOW(),
                                updated_by = :updatedBy
                            WHERE category_id = :id
                        """)
                .setParameter(
                        "id",
                        id)
                .setParameter(
                        "name",
                        category.getCategoryName())
                .setParameter(
                        "description",
                        category.getCategoryDescription())
                .setParameter(
                        "active",
                        category.isActive())
                .setParameter(
                        "updatedBy",
                        category.getUpdatedBy())
                .executeUpdate();

        return "redirect:/categories";
    }

    // DELETE
    @PostMapping("/delete/{id}")
    public String deleteCategory(
            @PathVariable Integer id) {

        entityManager
                .createNativeQuery("""
                            DELETE FROM categories
                            WHERE category_id = :id
                        """)
                .setParameter(
                        "id",
                        id)
                .executeUpdate();

        return "redirect:/categories";
    }
}