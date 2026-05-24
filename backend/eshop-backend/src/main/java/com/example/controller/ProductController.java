package com.example.controller;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.example.model.Product;

import com.example.service_interface.ProductService;

@Controller
@RequestMapping("/products")
@Transactional
public class ProductController {
        private final ProductService productService;

        public ProductController(ProductService productService) {
                this.productService = productService;
        }

        @GetMapping
        public String listProducts(Model model) {

                model.addAttribute("products", productService.getAllProducts());

                return "products/list";
        }

        @GetMapping("/detail/{id}")
        public String productDetail(
                        @PathVariable Integer id,
                        Model model) {

                Product product = productService.getProductById(id);

                model.addAttribute(
                                "product",
                                product);

                return "products/detail";
        }

        @GetMapping("/create")
        public String createForm(Model model) {

                model.addAttribute("product", new Product());

                return "products/create";
        }

        @PostMapping("/create")
        public String createProduct(
                        @ModelAttribute Product product) {

                productService.saveProduct(product);

                return "redirect:/products";
        }

        @GetMapping("/edit/{id}")
        public String editForm(
                        @PathVariable Integer id,
                        Model model) {

                Product product = productService.getProductById(id);

                model.addAttribute(
                                "product",
                                product);

                return "products/edit";
        }

        @PostMapping("/edit/{id}")
        public String editProduct(
                        @PathVariable Integer id,
                        @ModelAttribute Product product) {

                productService.editProduct(id, product);

                return "redirect:/products";
        }

        @PostMapping("/delete/{id}")
        public String deleteProduct(
                        @PathVariable Integer id) {

                productService.deleteProductById(id);

                return "redirect:/products";
        }
}