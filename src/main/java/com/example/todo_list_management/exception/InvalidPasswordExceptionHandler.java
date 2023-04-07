package com.example.todo_list_management.exception;

public class InvalidPasswordExceptionHandler extends RuntimeException{
    public InvalidPasswordExceptionHandler(String message) {
        super(message);
    }
}
