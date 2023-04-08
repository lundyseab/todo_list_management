package com.example.todo_list_management.service.serviceImp;

import com.example.todo_list_management.exception.EmptyDataExceptionHandler;
import com.example.todo_list_management.exception.FieldBlankExceptionHandler;
import com.example.todo_list_management.exception.NotFoundExceptionHandler;
import com.example.todo_list_management.model.entity.Category;
import com.example.todo_list_management.model.entity.UserApp;
import com.example.todo_list_management.model.request.CategoryRequest;
import com.example.todo_list_management.repository.CategoryRepository;
import com.example.todo_list_management.service.CategoryService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImp implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImp(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    private Integer getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserApp userApp = (UserApp) authentication.getPrincipal();
        return userApp.getId();
    }

    @Override
    public List<Category> getAllCategories() {

        if(categoryRepository.findAllCategory().isEmpty()) {
            throw new EmptyDataExceptionHandler("Data is empty");
        }

        return categoryRepository.findAllCategory();
    }

    @Override
    public Category getCategoryById(Integer categoryId) {

        if (categoryRepository.getCategoryById(categoryId) == null) {
            throw new NotFoundExceptionHandler("Category Id " + categoryId + " Not Found");
        }

        return categoryRepository.getCategoryById(categoryId);
    }

    @Override
    public Integer addNewCategory(CategoryRequest categoryRequest) {

        if(categoryRequest.getCategoryName().isBlank()) {
            throw new FieldBlankExceptionHandler("Field categoryName is blank");
        }
        else if(categoryRequest.getCategoryName().isEmpty()) {
            throw new FieldBlankExceptionHandler("Field categoryName is empty");
        }

        Integer categoryId = categoryRepository.saveCategory(categoryRequest, getCurrentUser());
        return categoryId;
    }

    @Override
    public boolean deleteCategoryById(Integer categoryId) {
        if(categoryRepository.getCategoryById(categoryId) == null) {
            throw new NotFoundExceptionHandler("Category not found");
        }
        return categoryRepository.deleteCategoryById(categoryId, getCurrentUser());
    }

    @Override
    public Integer updateCategory(CategoryRequest categoryRequest, Integer categoryId) {

        if(categoryRepository.getCategoryById(categoryId) == null) {
            throw new NotFoundExceptionHandler("User not found");
        }
        else if(categoryRequest.getCategoryName().isBlank()) {
            throw new FieldBlankExceptionHandler("Field categoryName is blank");
        }
        else if(categoryRequest.getCategoryName().isEmpty()) {
            throw new FieldBlankExceptionHandler("Field categoryName is empty");
        }

        Integer categoryIdStoreUpdate = categoryRepository.updateCategory(categoryRequest, categoryId, getCurrentUser());
        return categoryIdStoreUpdate;
    }

    @Override
    public List<Category> getAllCategoriesOfCurrentUser(Integer page, Integer size) {

        if(categoryRepository.findAllCategoryOfCurrentUser(page, size, getCurrentUser()).isEmpty()) {
            throw new EmptyDataExceptionHandler("Data is empty");
        }

        return categoryRepository.findAllCategoryOfCurrentUser(page, size, getCurrentUser());
    }

    @Override
    public List<Category> getAllCategoriesOfCurrentUserByDesc(Integer page, Integer size) {
        if(categoryRepository.findAllCategoryOfCurrentUser(page, size, getCurrentUser()).isEmpty()) {
            throw new EmptyDataExceptionHandler("Data is empty");
        }

        return categoryRepository.findAllCategoryOfCurrentUserByDesc(page, size, getCurrentUser());
    }

    @Override
    public Category getCategoryByCurrentUserId(Integer categoryId) {

        if(categoryRepository.getCategoryOfUserById(categoryId, getCurrentUser()) == null) {
            throw new NotFoundExceptionHandler("Category not found");
        }

        return categoryRepository.getCategoryOfUserById(categoryId, getCurrentUser());
    }

    @Override
    public List<Category> findAllCategoryOfCurrentUserForFilter() {

        if(categoryRepository.findAllCategoryOfCurrentUserForFilter(getCurrentUser()).isEmpty()) {
            throw new EmptyDataExceptionHandler("Data is empty");
        }

        return categoryRepository.findAllCategoryOfCurrentUserForFilter(getCurrentUser());
    }
}
