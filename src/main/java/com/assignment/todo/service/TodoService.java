package com.assignment.todo.service;

import com.assignment.todo.dto.Todo;
import org.springframework.stereotype.Service;
import com.assignment.todo.repository.TodoRepository;

import java.util.List;

@Service
public class TodoService {

    private final TodoRepository todoRepository;

    public TodoService(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    public Todo addTodoItem(Todo item) {
        return todoRepository.save(item);
    }

    public List<Todo> fetchAllTodoItems() {
        return (List<Todo>) todoRepository.findAll();
    }
}
