package com.assignment.todo.exception;

public class InvalidUserIdException extends RuntimeException{
    public InvalidUserIdException(String message) {
        super(message);
    }
}
