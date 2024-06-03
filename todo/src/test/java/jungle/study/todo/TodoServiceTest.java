package jungle.study.todo;

import jungle.study.todo.dto.TodoRequest;
import jungle.study.todo.dto.TodoResponse;
import jungle.study.todo.exception.TodoNotFoundException;
import jungle.study.todo.model.Todo;
import jungle.study.todo.repository.TodoRepository;
import jungle.study.todo.service.TodoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    private Todo todo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Test Todo");
        todo.setDescription("Test Description");
    }

    @Test
    void getAllTodos() {
        // Given
        List<Todo> todos = new ArrayList<>();
        todos.add(todo);
        when(todoRepository.findAll()).thenReturn(todos);

        // When
        List<TodoResponse> response = todoService.getAllTodos();

        // Then
        assertEquals(1, response.size());
        assertEquals("Test Todo", response.get(0).getTitle());
        verify(todoRepository, times(1)).findAll();
    }

    @Test
    void getTodoById() {
        // Given
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));

        // When
        TodoResponse response = todoService.getTodoById(1L);

        // Then
        assertNotNull(response);
        assertEquals("Test Todo", response.getTitle());
        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    void getTodoById_NotFound() {
        // Given
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(TodoNotFoundException.class, () -> {
            todoService.getTodoById(1L);
        });

        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    void createTodo() {
        // Given
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        TodoRequest request = new TodoRequest();
        request.setTitle("Test Todo");
        request.setDescription("Test Description");

        // When
        TodoResponse response = todoService.createTodo(request);

        // Then
        assertNotNull(response);
        assertEquals("Test Todo", response.getTitle());
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    void updateTodo() {
        // Given
        Todo todo = new Todo();
        todo.setId(1L);
        todo.setTitle("Original Title");
        todo.setDescription("Original Description");

        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        when(todoRepository.save(any(Todo.class))).thenReturn(todo);

        TodoRequest request = new TodoRequest();
        request.setTitle("Updated Title");
        request.setDescription("Updated Description");

        // When
        TodoResponse response = todoService.updateTodo(1L, request);

        // Then
        assertNotNull(response);
        assertEquals("Updated Title", response.getTitle());
        verify(todoRepository, times(1)).findById(1L);

        ArgumentCaptor<Todo> todoCaptor = ArgumentCaptor.forClass(Todo.class);
        verify(todoRepository, times(1)).save(todoCaptor.capture());
        Todo capturedTodo = todoCaptor.getValue();
        assertEquals("Updated Title", capturedTodo.getTitle());
        assertEquals("Updated Description", capturedTodo.getDescription());
    }

    @Test
    void updateTodo_NotFound() {
        // Given
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        TodoRequest request = new TodoRequest();
        request.setTitle("Updated Title");
        request.setDescription("Updated Description");

        // When & Then
        assertThrows(TodoNotFoundException.class, () -> {
            todoService.updateTodo(1L, request);
        });

        verify(todoRepository, times(1)).findById(1L);
    }

    @Test
    void deleteTodo() {
        // Given
        when(todoRepository.findById(1L)).thenReturn(Optional.of(todo));
        doNothing().when(todoRepository).deleteById(1L);

        // When
        todoService.deleteTodo(1L);

        // Then
        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(1)).deleteById(1L);
    }

    @Test
    void deleteTodo_NotFound() {
        // Given
        when(todoRepository.findById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(TodoNotFoundException.class, () -> {
            todoService.deleteTodo(1L);
        });

        verify(todoRepository, times(1)).findById(1L);
        verify(todoRepository, times(0)).deleteById(1L);
    }
}