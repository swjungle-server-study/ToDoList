package jungle.study.todo.controller;

import jungle.study.todo.domain.Todo;
import jungle.study.todo.dto.ResponseEnvelope;
import jungle.study.todo.dto.TodoDto;
import jungle.study.todo.service.impl.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/api/todo")
public class TodoController {
    private final TodoServiceImpl todoService;

    @Autowired
    public TodoController(TodoServiceImpl todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<ResponseEnvelope<List<TodoDto>>> getAllTodos() {
        List<TodoDto> todos = todoService.findTodo().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(new ResponseEnvelope<>(null, todos, "Todos fetched successfully"));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseEnvelope<TodoDto>> getTodoById(@PathVariable Long id) {
        return todoService.findTodoOne(id)
                .map(todo -> ResponseEntity.ok(new ResponseEnvelope<>(null, convertToDTO(todo), "Todo fetched successfully")))
                .orElse(ResponseEntity.status(404).body(new ResponseEnvelope<>("404", null, "Todo not found")));
    }

    @PostMapping
    public ResponseEntity<ResponseEnvelope<TodoDto>> addTodoItem(@RequestBody TodoDto todoDto) {
        Todo newTodo = todoService.save(convertToEntity(todoDto));
        return ResponseEntity.status(201).body(new ResponseEnvelope<>(null, convertToDTO(newTodo), "Todo created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEnvelope<TodoDto>> updateTodoItem(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        try {
            Todo updatedTodo = todoService.update(id, convertToEntity(todoDto));
            ResponseEnvelope<TodoDto> response = new ResponseEnvelope<>(null, convertToDTO(updatedTodo), "Todo updated successfully");
            return ResponseEntity.ok(response);
        } catch (Exception ex) {
            return ResponseEntity.status(404).body(new ResponseEnvelope<>("404", null, "Todo not found"));
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEnvelope<Void>> deleteTodoItem(@PathVariable Long id) {
        boolean deleted = todoService.delete(id);
        if (deleted) {
            return ResponseEntity.ok(new ResponseEnvelope<>(null, null, "Todo deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(new ResponseEnvelope<>("404", null, "Todo not found"));
        }
    }

    private TodoDto convertToDTO(Todo todo) {
        TodoDto dto = new TodoDto();
        dto.setId(todo.getId());
        dto.setTitle(todo.getTitle());
        dto.setStatus(todo.getStatus());
        return dto;
    }

    private Todo convertToEntity(TodoDto dto) {
        Todo todo = new Todo(dto.getTitle(), dto.getContents(), dto.getStatus());
        return todo;
    }
}