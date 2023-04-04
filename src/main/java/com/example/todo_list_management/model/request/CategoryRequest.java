package com.example.todo_list_management.model.request;

import lombok.*;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryRequest {
    private String categoryName;
}
