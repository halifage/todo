package com.assignment.todo.controller;

import com.assignment.todo.dto.Todo;
import com.assignment.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;

import javax.validation.Valid;
import java.util.List;

@RestController
public class TodoController {

    private final TodoService todoService;

    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todo")
    public ResponseEntity<Todo> addTodoItem(@Valid @RequestBody Todo item) {
        Todo addedItem = todoService.addTodoItem(item);
        return new ResponseEntity<>(addedItem, HttpStatus.OK);
    }

    @GetMapping("/todo")
    public ResponseEntity<List<Todo>> fetchAllTodoItems(@RequestParam long userId) {
        List<Todo> allTodoItems = todoService.fetchAllTodoItems();
        return new ResponseEntity<>(allTodoItems, HttpStatus.OK);
    }

    @PutMapping("/todo")
    public ResponseEntity<Todo> updateTodoItem(@Valid @RequestBody Todo item) {
        Todo updatedItem = todoService.updateTodoItem(item);
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }

    @DeleteMapping("/todo")
    public ResponseEntity<Void> deleteTodoItem(@Valid @RequestBody Todo item) {
        boolean isDeleted = todoService.deleteTodoItem(item);
        if (!isDeleted) {
            throw new HttpServerErrorException(HttpStatus.BAD_REQUEST, "Unable to delete item with ID: " + item.id());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
