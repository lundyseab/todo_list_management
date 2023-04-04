package com.example.todo_list_management.exception;

public class EmptyDataExceptionHandler extends RuntimeException{
    public EmptyDataExceptionHandler(String message) {
        super(message);
    }
}
