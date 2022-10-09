package com.assignment.todo.exception;

public class TodoItemDeleteException extends RuntimeException{
    public TodoItemDeleteException(String message) {
        super(message);
    }
}
