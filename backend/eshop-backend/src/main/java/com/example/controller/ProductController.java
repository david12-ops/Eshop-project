package com.example.controller;

import java.util.List;

import org.springframework.web.bind.annotation.*;

import com.example.model.Product;
import com.example.service_interface.ProductService;

@RestController 
@RequestMapping("/products")
public class ProductController {
        private final ProductService productService;

        public ProductController(ProductService productService) {
                this.productService = productService;
        }

        // LIST
        @GetMapping
        public List<Product> listProducts() {
                return productService.getProducts();
        }

        // DETAIL
        @GetMapping("/{id}")
        public Product productDetail(@PathVariable Integer id) {
                return productService.getProductById(id);
        }

        // CREATE
        @PostMapping("/create")
        public Product createProduct(@RequestBody Product product) {
                return productService.saveProduct(product);
        }

        // EDIT
        @PutMapping("/{id}")
        public Product editProduct(
                        @PathVariable Integer id,
                        @RequestBody Product product) {
                return productService.editProduct(id, product);
        }

        // DELETE
        @DeleteMapping ("/{id}")
        public void deleteProduct(@PathVariable Integer id) {
                productService.deleteProductById(id);
        }
}