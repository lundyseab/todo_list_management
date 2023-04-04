package com.example.todo_list_management.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TaskRequest {
    private String taskName;
    private String description;
    private String status;
    private Integer categoryId;
    private Timestamp date;

}
