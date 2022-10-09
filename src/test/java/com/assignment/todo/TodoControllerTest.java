package com.assignment.todo;

import com.assignment.todo.controller.TodoController;
import com.assignment.todo.dto.Todo;
import com.assignment.todo.exception.InvalidUserIdException;
import com.assignment.todo.exception.TodoItemDeleteException;
import com.assignment.todo.service.TodoService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import java.time.LocalDate;

import static com.assignment.todo.enums.State.TODO;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.findAndRegisterModules(); // To handle parsing of LocalDate to JSON
    }

    @Test
    public void shouldThrowException_whenUserIdIsLessThanOne() throws Exception {
        mockMvc.perform(get("/todo").param("userId", "0"))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof InvalidUserIdException));
    }

    @Test
    public void shouldThrowException_whenUserIdIsMissing() throws Exception {
        mockMvc.perform(get("/todo"))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MissingServletRequestParameterException));
    }

    @Test
    public void shouldThrowException_whenUserIdIsEmpty() throws Exception {
        mockMvc.perform(get("/todo").param("userId", ""))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentTypeMismatchException));
    }

    @Test
    public void shouldThrowException_whenAddingTodoItemWithInvalidId() throws Exception {
        Todo itemToAdd = new Todo(0, 1, "some description", LocalDate.now(), TODO);
        mockMvc.perform(post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemToAdd)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    public void shouldThrowException_whenAddingTodoItemWithInvalidUserId() throws Exception {
        Todo itemToAdd = new Todo(1, 0, "some description", LocalDate.now(), TODO);
        mockMvc.perform(post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemToAdd)))
                .andExpect(status().isUnauthorized())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    public void shouldThrowException_whenAddingTodoItemWithInvalidDescription() throws Exception {
        Todo itemToAdd = new Todo(1, 1, "", LocalDate.now(), TODO);
        mockMvc.perform(post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemToAdd)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    public void shouldThrowException_whenAddingTodoItemWithInvalidState() throws Exception {
        Todo itemToAdd = new Todo(1, 1, "some description", LocalDate.now(), null);
        mockMvc.perform(post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemToAdd)))
                .andExpect(status().isBadRequest())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof MethodArgumentNotValidException));
    }

    @Test
    public void shouldThrowException_whenFailingToDeleteItem() throws Exception {
        Todo itemToDelete = new Todo(1, 1, "some description", LocalDate.now(), TODO);
        Mockito.when(todoService.deleteTodoItem(any(Todo.class))).thenReturn(false);
        mockMvc.perform(delete("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(itemToDelete)))
                .andExpect(status().isInternalServerError())
                .andExpect(result -> assertTrue(result.getResolvedException() instanceof TodoItemDeleteException));
    }
}
