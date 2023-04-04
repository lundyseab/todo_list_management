package com.example.todo_list_management.exception;

public class UserDuplicateExceptionHandler extends RuntimeException{
    public UserDuplicateExceptionHandler(String message) {
        super(message);
    }
}
