package com.assignment.todo.service;

import com.assignment.todo.dto.Todo;
import com.assignment.todo.repository.TodoRepository;
import org.springframework.stereotype.Service;

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

    public Todo updateTodoItem(Todo item) {
        return todoRepository.save(item);
    }

    public boolean deleteTodoItem(Todo item) {
        todoRepository.delete(item);
        return !todoRepository.existsById(String.valueOf(item.id()));
    }
}
