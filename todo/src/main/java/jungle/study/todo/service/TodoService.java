package jungle.study.todo.service;

import jungle.study.todo.common.error.TodoNotFoundException;
import jungle.study.todo.domain.Todo;
import jungle.study.todo.dto.TodoRequestDto;
import jungle.study.todo.dto.TodoResponseDto;
import jungle.study.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final AtomicLong sequence = new AtomicLong(1);

    public TodoResponseDto createTodo(TodoRequestDto todoDto) {
        if (todoDto.getTitle() == null || todoDto.getTitle().isBlank() ||
                todoDto.getContent() == null || todoDto.getContent().isBlank()) {
            throw new IllegalArgumentException("Title and content are required");
        }
        long newId = sequence.getAndIncrement();
        Todo newTodo = Todo.of(newId, todoDto.getTitle(), todoDto.getContent(), todoDto.isCompleted());
        return new TodoResponseDto(todoRepository.save(newTodo));
    }

    public List<Todo> getAllTodos() {
        return todoRepository.findAll();
    }
    public Todo getTodoById(Long id) {
        return todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
    }

    public TodoResponseDto updateTodo(Long id, TodoRequestDto updatedTodo) {
        if (updatedTodo.getTitle() == null || updatedTodo.getTitle().isBlank() ||
                updatedTodo.getContent() == null || updatedTodo.getContent().isBlank()) {
            throw new IllegalArgumentException("Title and content are required");
        }
        Todo existingTodo = todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        existingTodo.update(updatedTodo);
        return new TodoResponseDto(todoRepository.save(existingTodo));
    }

    public void deleteTodo(Long id) {
        todoRepository.findById(id)
                .orElseThrow(() -> new TodoNotFoundException(id));
        todoRepository.deleteById(id);
    }
}