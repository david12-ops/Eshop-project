package com.example.service_interface;

import com.example.dto.ProductDTO;
import com.example.model.MyUserDetail;

import java.util.List;

public interface ProductService {

    ProductDTO getProductById(Integer id, MyUserDetail userDetail);

    List<ProductDTO> getProducts(MyUserDetail userDetail);

    ProductDTO saveProduct(ProductDTO productDTO, MyUserDetail userDetail);

    ProductDTO editProduct(Integer id, ProductDTO productDTO, MyUserDetail userDetail);

    void deleteProductById(Integer id, MyUserDetail userDetail);
}
