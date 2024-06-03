package jungle.study.todo.service;

import jungle.study.todo.dto.TodoRequest;
import jungle.study.todo.dto.TodoResponse;
import jungle.study.todo.exception.TodoNotFoundException;
import jungle.study.todo.model.Todo;
import jungle.study.todo.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class TodoServiceImpl implements TodoService {

    @Autowired
    private TodoRepository todoRepository;

    public List<TodoResponse> getAllTodos() {
        return todoRepository.findAll().stream().map(this::mapToResponse).collect(Collectors.toList());
    }

    public TodoResponse getTodoById(Long id) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo not found"));
        return mapToResponse(todo);
    }

    public TodoResponse createTodo(TodoRequest todoRequest) {
        Todo todo = new Todo();
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        Todo savedTodo = todoRepository.save(todo);
        return mapToResponse(savedTodo);
    }

    public TodoResponse updateTodo(Long id, TodoRequest todoRequest) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo not found"));
        todo.setTitle(todoRequest.getTitle());
        todo.setDescription(todoRequest.getDescription());
        Todo updatedTodo = todoRepository.save(todo);
        return mapToResponse(updatedTodo);
    }

    public TodoResponse checkTodo(Long id,TodoRequest todoRequest) {
        Todo todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo not found"));
        todo.setStatus(!todo.isStatus());
        Todo checkTodo = todoRepository.save(todo);
        return mapToResponse(checkTodo);
    }

    public void deleteTodo(Long id) {
        todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException("Todo not found"));
        todoRepository.deleteById(id);
    }

    public TodoResponse mapToResponse(Todo todo) {
        TodoResponse response = new TodoResponse();
        response.setId(todo.getId());
        response.setTitle(todo.getTitle());
        response.setDescription(todo.getDescription());
        response.setChecked(todo.isStatus());
        return response;
    }
}

