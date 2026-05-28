package com.example.service_interface;

import java.util.List;

import com.example.dto.ProductCardDto;
import com.example.model.Product;

public interface ProductService {

    Product getProductById(Integer id);

    List<Product> getAllProducts();

    void saveProduct(Product product);

    void editProduct(Integer id, Product product);

    void deleteProductById(Integer id);

    List<ProductCardDto> getFeaturedProducts();
}
