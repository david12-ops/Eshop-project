package com.example.controller;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Category;
import com.example.model.Product;
import com.example.model.view.ProductCategoryView;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Controller
@RequestMapping("/products")
@Transactional
public class ProductController {

    @PersistenceContext
    private EntityManager entityManager;

    @SuppressWarnings("unchecked")
    @GetMapping
    public String listProducts(Model model) {

        List<Object[]> rows = entityManager
                .createNativeQuery("""
                            SELECT *
                            FROM vw_products_with_categories
                        """)
                .getResultList();

        List<ProductCategoryView> products = rows.stream()
                .map(row -> {

                    ProductCategoryView p = new ProductCategoryView();

                    p.setProductId((Integer) row[0]);
                    p.setProductName((String) row[1]);
                    p.setProductCode((String) row[2]);
                    p.setProductDescription((String) row[3]);

                    p.setRecommendedPrice((BigDecimal) row[4]);
                    p.setUnitPrice((BigDecimal) row[5]);
                    p.setTaxRate((BigDecimal) row[6]);

                    p.setCategoryId((Integer) row[7]);

                    p.setProductIsActive((Boolean) row[8]);

                    p.setProductCreatedAt(
                            (OffsetDateTime) row[9]);

                    p.setProductUpdatedAt(
                            (OffsetDateTime) row[10]);

                    p.setCategoryName((String) row[11]);

                    p.setCategoryDescription(
                            (String) row[12]);

                    p.setCategoryIsActive(
                            (Boolean) row[13]);

                    p.setCategoryCreatedAt(
                            (OffsetDateTime) row[14]);

                    p.setCategoryUpdatedAt(
                            (OffsetDateTime) row[15]);

                    return p;
                })
                .toList();

        model.addAttribute(
                "products",
                products);

        return "products/list";
    }

    @GetMapping("/detail/{id}")
    public String productDetail(
            @PathVariable Integer id,
            Model model) {

        Product product = entityManager
                .createQuery("""
                            SELECT p
                            FROM Product p
                            LEFT JOIN FETCH p.category
                            WHERE p.id = :id
                        """, Product.class)
                .setParameter("id", id)
                .getSingleResult();

        model.addAttribute(
                "product",
                product);

        return "products/detail";
    }

    @GetMapping("/create")
    public String createForm(Model model) {

        List<Category> categories = entityManager
                .createQuery("""
                            SELECT c
                            FROM Category c
                            ORDER BY c.categoryName
                        """, Category.class)
                .getResultList();

        model.addAttribute(
                "product",
                new Product());

        model.addAttribute(
                "categories",
                categories);

        return "products/create";
    }

    @PostMapping("/create")
    public String createProduct(
            @ModelAttribute Product product) {

        Integer categoryId = null;

        if (product.getCategory() != null) {
            categoryId = product.getCategory().getId();
        }

        entityManager
                .createNativeQuery("""
                            INSERT INTO products
                            (
                                product_name,
                                product_code,
                                product_description,
                                product_image_url,
                                recommended_price,
                                unit_price,
                                tax_rate,
                                category_id,
                                is_active,
                                created_at,
                                updated_at,
                                created_by,
                                updated_by
                            )
                            VALUES
                            (
                                :productName,
                                :productCode,
                                :productDescription,
                                :productImageUrl,
                                :recommendedPrice,
                                :unitPrice,
                                :taxRate,
                                :categoryId,
                                :active,
                                NOW(),
                                NOW(),
                                :createdBy,
                                :updatedBy
                            )
                        """)
                .setParameter(
                        "productName",
                        product.getProductName())
                .setParameter(
                        "productCode",
                        product.getProductCode())
                .setParameter(
                        "productDescription",
                        product.getProductDescription())
                .setParameter(
                        "productImageUrl",
                        product.getProductImageUrl())
                .setParameter(
                        "recommendedPrice",
                        product.getRecommendedPrice())
                .setParameter(
                        "unitPrice",
                        product.getUnitPrice())
                .setParameter(
                        "taxRate",
                        product.getTaxRate())
                .setParameter(
                        "categoryId",
                        categoryId)
                .setParameter(
                        "active",
                        product.isActive())
                .setParameter(
                        "createdBy",
                        product.getCreatedBy())
                .setParameter(
                        "updatedBy",
                        product.getUpdatedBy())
                .executeUpdate();

        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String editForm(
            @PathVariable Integer id,
            Model model) {

        Product product = entityManager
                .createQuery("""
                            SELECT p
                            FROM Product p
                            LEFT JOIN FETCH p.category
                            WHERE p.id = :id
                        """, Product.class)
                .setParameter("id", id)
                .getSingleResult();

        List<Category> categories = entityManager
                .createQuery("""
                            SELECT c
                            FROM Category c
                            ORDER BY c.categoryName
                        """, Category.class)
                .getResultList();

        model.addAttribute(
                "product",
                product);

        model.addAttribute(
                "categories",
                categories);

        return "products/edit";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(
            @PathVariable Integer id,
            @ModelAttribute Product product) {

        Integer categoryId = null;

        if (product.getCategory() != null) {
            categoryId = product.getCategory().getId();
        }

        entityManager
                .createNativeQuery("""
                            UPDATE products
                            SET
                                product_name = :productName,
                                product_code = :productCode,
                                product_description = :productDescription,
                                product_image_url = :productImageUrl,
                                recommended_price = :recommendedPrice,
                                unit_price = :unitPrice,
                                tax_rate = :taxRate,
                                category_id = :categoryId,
                                is_active = :active,
                                updated_at = NOW(),
                                updated_by = :updatedBy
                            WHERE product_id = :id
                        """)
                .setParameter(
                        "id",
                        id)
                .setParameter(
                        "productName",
                        product.getProductName())
                .setParameter(
                        "productCode",
                        product.getProductCode())
                .setParameter(
                        "productDescription",
                        product.getProductDescription())
                .setParameter(
                        "productImageUrl",
                        product.getProductImageUrl())
                .setParameter(
                        "recommendedPrice",
                        product.getRecommendedPrice())
                .setParameter(
                        "unitPrice",
                        product.getUnitPrice())
                .setParameter(
                        "taxRate",
                        product.getTaxRate())
                .setParameter(
                        "categoryId",
                        categoryId)
                .setParameter(
                        "active",
                        product.isActive())
                .setParameter(
                        "updatedBy",
                        product.getUpdatedBy())
                .executeUpdate();

        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(
            @PathVariable Integer id) {

        entityManager
                .createNativeQuery("""
                            DELETE FROM products
                            WHERE product_id = :id
                        """)
                .setParameter(
                        "id",
                        id)
                .executeUpdate();

        return "redirect:/products";
    }
}