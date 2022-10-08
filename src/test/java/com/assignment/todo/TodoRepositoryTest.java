package com.assignment.todo;

import com.assignment.todo.dto.Todo;
import com.assignment.todo.enums.State;
import com.assignment.todo.repository.TodoRepository;
import config.RedisConfigurationTest;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.assignment.todo.enums.State.*;
import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = RedisConfigurationTest.class)
public class TodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    @BeforeEach
    public void flushDataSource() {
        todoRepository.deleteAll();
    }

    @Test
    public void shouldAddTodoItem() {
        Todo addedItem = addTodoItem(1, State.IN_PROGRESS);
        List<Todo> todos = getAllTodoItems();
        assertEquals(1, todos.size());

        Todo fetchedItem = todos.get(0);
        assertEquals(addedItem.id(), fetchedItem.id());
        assertEquals(addedItem.userId(), fetchedItem.userId());
        assertEquals(addedItem.state(), fetchedItem.state());
        assertEquals(addedItem.dueDate(), fetchedItem.dueDate());
        assertEquals(addedItem.description(), fetchedItem.description());
    }

    @Test
    public void shouldDeleteTodoItem() {
        Todo addedTodoItem = addTodoItem(1, TODO);
        todoRepository.delete(addedTodoItem);

        assertFalse(todoRepository.existsById(String.valueOf(addedTodoItem.id())));
    }

    @Test
    public void shouldUpdateTodoItem() {
        Todo addedTodoItem = addTodoItem(2, TODO);
        Todo updatedTodoItem = new Todo(addedTodoItem.id(), 2365, "updated todo", addedTodoItem.dueDate(), DONE);
        todoRepository.save(updatedTodoItem);

        Todo fetchedUpdatedTodoItem = todoRepository.findById(String.valueOf(addedTodoItem.id())).orElse(null);
        assertNotNull(fetchedUpdatedTodoItem);

        assertEquals(updatedTodoItem.id(), fetchedUpdatedTodoItem.id());
        assertEquals(updatedTodoItem.description(), fetchedUpdatedTodoItem.description());
        assertEquals(updatedTodoItem.state(), fetchedUpdatedTodoItem.state());
    }

    @Test
    public void shouldFetchAllTodoItems() {
        Todo todoItem1 = addTodoItem(1, IN_PROGRESS);
        Todo todoItem2 = addTodoItem(2, DONE);

        List<Todo> allTodoItems = getAllTodoItems();

        assertEquals(2, allTodoItems.size());
    }

    /* utility methods */
    private Todo addTodoItem(long id, State state) {
        return todoRepository.save(new Todo(id, 23, "test todo", LocalDate.now(), state));
    }

    private List<Todo> getAllTodoItems() {
        return (List<Todo>) todoRepository.findAll();
    }

}
