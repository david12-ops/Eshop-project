package com.example.service_interface;

import java.util.List;

import com.example.model.Product;

public interface ProductService {

    Product getProductById(Integer id);

    List<Product> getProducts();

    Product saveProduct(Product product);

    Product editProduct(Integer id, Product product);

    void deleteProductById(Integer id);
}
