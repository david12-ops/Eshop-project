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

import com.example.dto.ProductDTO;
import com.example.model.MyUserDetail;
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
        public List<ProductDTO> listProducts(@AuthenticationPrincipal MyUserDetail userDetail) {
                return productService.getProducts(userDetail);
        }

        // DETAIL
        @GetMapping("/{id}")
        public ProductDTO productDetail(
                        @PathVariable Integer id,
                        @AuthenticationPrincipal MyUserDetail userDetail) {
                return productService.getProductById(id, userDetail);
        }

        // CREATE
        @PostMapping("/create")
        public ProductDTO createProduct(
                        @RequestBody ProductDTO productDTO,
                        @AuthenticationPrincipal MyUserDetail userDetail) {
                return productService.saveProduct(productDTO, userDetail);
        }

        // EDIT
        @PutMapping("/{id}")
        public ProductDTO editProduct(
                        @PathVariable Integer id,
                        @RequestBody ProductDTO productDTO,
                        @AuthenticationPrincipal MyUserDetail userDetail) {
                return productService.editProduct(id, productDTO, userDetail);
        }

        // DELETE
        @DeleteMapping("/{id}")
        public void deleteProduct(
                        @PathVariable Integer id,
                        @AuthenticationPrincipal MyUserDetail userDetail) {
                productService.deleteProductById(id, userDetail);
        }
}