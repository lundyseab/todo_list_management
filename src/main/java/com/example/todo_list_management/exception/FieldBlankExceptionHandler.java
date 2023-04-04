package com.example.todo_list_management.exception;

public class FieldBlankExceptionHandler extends RuntimeException {
    public FieldBlankExceptionHandler(String message) {
        super(message);
    }
}
