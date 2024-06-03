package jungle.study.todo.controller;

import jungle.study.todo.dto.Envelope;
import jungle.study.todo.dto.TodoRequest;
import jungle.study.todo.dto.TodoResponse;
import jungle.study.todo.service.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/todos")
public class TodoController {

    @Autowired
    private TodoServiceImpl todoService;

    @GetMapping
    public ResponseEntity<Envelope<List<TodoResponse>>> getAllTodos() {
        List<TodoResponse> todos = todoService.getAllTodos();
        return ResponseEntity.ok(new Envelope<>(todos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Envelope<TodoResponse>> getTodoById(@PathVariable Long id) {
        TodoResponse todo = todoService.getTodoById(id);
        return ResponseEntity.ok(new Envelope<>(todo));
    }

    @PostMapping
    public ResponseEntity<Envelope<TodoResponse>> createTodo(@RequestBody TodoRequest todoRequest) {
        TodoResponse todo = todoService.createTodo(todoRequest);
        return ResponseEntity.ok(new Envelope<>(todo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Envelope<TodoResponse>> updateTodo(@PathVariable Long id, @RequestBody TodoRequest todoRequest) {
        TodoResponse todo = todoService.updateTodo(id, todoRequest);
        return ResponseEntity.ok(new Envelope<>(todo));
    }

    @PutMapping("/check/{id}")
    public ResponseEntity<Envelope<TodoResponse>> checkTodo(@PathVariable Long id, @RequestBody TodoRequest todoRequest) {
        TodoResponse todo = todoService.checkTodo(id, todoRequest);
        return ResponseEntity.ok(new Envelope<>(todo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Envelope<Void>> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok(new Envelope<>(null));
    }
}
