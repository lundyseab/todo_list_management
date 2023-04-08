package com.example.todo_list_management.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Task {
    private Integer taskId;
    private String taskName;
    private String description;
    private Timestamp date;
    private Integer userId;
    private String status;
    private Integer categoryId;
    private Category category;
}
