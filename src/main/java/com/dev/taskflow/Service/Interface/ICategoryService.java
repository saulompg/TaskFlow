package com.dev.taskflow.Service.Interface;

import com.dev.taskflow.DTOs.CategoryCreateDTO;
import com.dev.taskflow.DTOs.CategoryDTO;
import com.dev.taskflow.DTOs.CategoryUpdateDTO;

import java.util.List;

public interface ICategoryService {
    List<CategoryDTO> getCategories(String name);
    CategoryDTO getCategory(Long id);
    CategoryDTO createCategory(CategoryCreateDTO dto);
    CategoryDTO updateCategory(Long id, CategoryUpdateDTO dto);
    void  deleteCategory(Long id);
}
