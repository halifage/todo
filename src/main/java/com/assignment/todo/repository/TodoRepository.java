package com.assignment.todo.repository;

import com.assignment.todo.dto.Todo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TodoRepository extends CrudRepository<Todo, String> {
}
