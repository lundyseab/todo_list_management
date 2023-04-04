package com.example.todo_list_management.model.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Category {
    private  Integer categoryId;
    private String categoryName;
    private Integer userId;
    private Timestamp date;
}
