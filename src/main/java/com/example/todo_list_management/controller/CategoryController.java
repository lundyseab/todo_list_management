package com.example.todo_list_management.controller;

import com.example.todo_list_management.model.entity.Category;
import com.example.todo_list_management.model.entity.UserApp;
import com.example.todo_list_management.model.request.CategoryRequest;
import com.example.todo_list_management.model.response.BodyResponse;
import com.example.todo_list_management.service.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/categories")
@SecurityRequirement(name = "bearerAuth")
public class CategoryController {
    private final CategoryService categoryService;

    private <T> ResponseEntity getBodyResponse(HttpStatus httpStatus, Boolean success, T payload) {
        BodyResponse<T> response = BodyResponse.<T>builder()
                .success(success)
                .date(new  Timestamp(System.currentTimeMillis()))
                .payload(payload)
                .build();
        return ResponseEntity.status(httpStatus).body(response);
    }
    @GetMapping("")
    @Operation(summary = "Get all Categories")
    public ResponseEntity<BodyResponse<List<Category>>> getAllCategories() {
        return getBodyResponse(HttpStatus.OK, true, categoryService.getAllCategories());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get Category By Id")
    public ResponseEntity<BodyResponse<Category>> getCategoryById(@PathVariable("id") Integer categoryId) {
        return getBodyResponse(HttpStatus.OK, true, categoryService.getCategoryById(categoryId));
    }

    @PostMapping("")
    @Operation(summary = "Add New Category")
    public ResponseEntity<BodyResponse<Category>> addNewCategory(@RequestBody CategoryRequest categoryRequest) {
        Integer storeCategoryId = categoryService.addNewCategory(categoryRequest);
        return getBodyResponse(HttpStatus.OK, true, categoryService.getCategoryById(storeCategoryId));
    }


    @DeleteMapping("/{id}/users")
    @Operation(summary = "Delete Category by Id for current user")
    public ResponseEntity<BodyResponse<Boolean>> deleteCategoryById(@PathVariable("id") Integer categoryId) {
        return getBodyResponse(HttpStatus.OK, true, categoryService.deleteCategoryById(categoryId));
    }

    @PutMapping("/{id}/users")
    @Operation(summary = "Update Categories ")
    public ResponseEntity<BodyResponse<Category>> updateCategory(@RequestBody CategoryRequest categoryRequest, @PathVariable("id") Integer categoryId) {
        Integer id = categoryService.updateCategory(categoryRequest, categoryId);
        return getBodyResponse(HttpStatus.OK, true, categoryService.getCategoryById(id));
    }

    @GetMapping("/users")
    @Operation(summary = "Get all Categories For Current Users")
    public ResponseEntity<BodyResponse<List<Category>>> getAllCategoriesOfCurrentUsers(@RequestParam Integer page, @RequestParam Integer size, @RequestParam  Boolean asc, @RequestParam  Boolean desc) {

        if((!desc && !asc) || desc) {
            return getBodyResponse(HttpStatus.OK, true, categoryService.getAllCategoriesOfCurrentUserByDesc(page, size));
        }
        return getBodyResponse(HttpStatus.OK, true, categoryService.getAllCategoriesOfCurrentUser(page, size));
    }

    @GetMapping("/{id}/users")
    @Operation(summary = "Get Category For Current User By Id")
    public ResponseEntity<BodyResponse<Category>> getCategoryByCurrentUserId(@PathVariable("id") Integer categoryId) {
        return getBodyResponse(HttpStatus.OK, true, categoryService.getCategoryByCurrentUserId(categoryId));
    }

    @GetMapping("/all")
    @Operation(summary = "Get Category For Current User")
    public ResponseEntity<BodyResponse<List<Category>>> getAllCategoriesByCurrentUser() {
        return getBodyResponse(HttpStatus.OK, true, categoryService.findAllCategoryOfCurrentUserForFilter());
    }
}
