package com.example.service_interface;

import com.example.dto.CategoryDTO;
import com.example.model.MyUserDetail;

import java.util.List;

public interface CategoryService {

    List<CategoryDTO> getCategories(MyUserDetail userDetail);

    CategoryDTO saveCategory(CategoryDTO categoryDTO, MyUserDetail userDetail);

    void deleteCategoryById(Integer id, MyUserDetail userDetail);

    CategoryDTO editCategory(Integer id, CategoryDTO categoryDTO, MyUserDetail userDetail);

    CategoryDTO getCategoryById(Integer id, MyUserDetail userDetail);
}
