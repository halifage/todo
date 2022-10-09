package com.assignment.todo.controller;

import com.assignment.todo.dto.Todo;
import com.assignment.todo.exception.InvalidUserIdException;
import com.assignment.todo.exception.TodoItemDeleteException;
import com.assignment.todo.service.TodoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static com.assignment.todo.constants.Constants.AUTHENTICATION_ERROR_MESSAGE;
import static com.assignment.todo.constants.Constants.ERROR_DELETING_ITEM_MESSAGE;

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
        if (userId < 1) {
            throw new InvalidUserIdException(AUTHENTICATION_ERROR_MESSAGE);
        }
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
            throw new TodoItemDeleteException(ERROR_DELETING_ITEM_MESSAGE + item.id());
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
