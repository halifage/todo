package com.assignment.todo.dto;

import com.assignment.todo.enums.State;
import org.springframework.data.redis.core.RedisHash;

import java.io.Serializable;
import java.time.LocalDate;

import static com.assignment.todo.constants.Constants.TODO;

@RedisHash(TODO)
public record Todo(long id, long userId, String description, LocalDate dueDate, State state) implements Serializable { }
