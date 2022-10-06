package com.assignment.todo.controller;

import com.assignment.todo.dto.Todo;
import com.assignment.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }


    @PostMapping("/todo")
    public ResponseEntity<Todo> addTodoItem(@RequestBody Todo itemToAdd) {
        Todo addedItem = todoService.addTodoItem(itemToAdd);
        return new ResponseEntity<>(addedItem, HttpStatus.OK);
    }

    @GetMapping("/todo")
    public ResponseEntity<List<Todo>> fetchAllTodoItems() {
        List<Todo> allTodoItems = todoService.fetchAllTodoItems();
        return new ResponseEntity<>(allTodoItems, HttpStatus.OK);
    }
}
