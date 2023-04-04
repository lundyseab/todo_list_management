package com.example.todo_list_management.exception;

public class NotFoundExceptionHandler extends RuntimeException{
    public NotFoundExceptionHandler(String message) {
        super(message);
    }
}
