package com.assignment.todo.exception;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
public record ExceptionDto(LocalDateTime timestamp, HttpStatus status, String message) { }
