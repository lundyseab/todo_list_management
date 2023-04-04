package com.example.todo_list_management.service;

import com.example.todo_list_management.model.entity.Category;
import com.example.todo_list_management.model.request.CategoryRequest;

import java.util.List;

public interface CategoryService {
    List<Category> getAllCategories();
    Category getCategoryById (Integer categoryId);

    Integer addNewCategory(CategoryRequest categoryRequest);

    boolean deleteCategoryById(Integer categoryId);

    Integer updateCategory(CategoryRequest categoryRequest, Integer categoryId);

    List<Category> getAllCategoriesOfCurrentUser(Integer page, Integer size);
    List<Category> getAllCategoriesOfCurrentUserByDesc(Integer page, Integer size);
    Category getCategoryByCurrentUserId(Integer categoryId);
}
