package com.example.todo_list_management.exception;

public class FieldEmptyExceptionHandler extends RuntimeException {
    public FieldEmptyExceptionHandler(String message) {
        super(message);
    }
}
