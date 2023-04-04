package com.example.todo_list_management.exception;

public class NotValidValueExceptionHandler extends RuntimeException{
    public NotValidValueExceptionHandler(String message) {
        super(message);
    }
}
