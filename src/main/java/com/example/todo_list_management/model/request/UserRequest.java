package com.example.todo_list_management.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@AllArgsConstructor

@NoArgsConstructor
@Builder
public class UserRequest {
        private Integer UserId;
        private String email;
        private Integer password;
        private String role;
}
