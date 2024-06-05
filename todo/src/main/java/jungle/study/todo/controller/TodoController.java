package jungle.study.todo.controller;

import jungle.study.todo.dto.ResponseEnvelope;
import jungle.study.todo.dto.TodoDto;
import jungle.study.todo.service.impl.TodoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/api")
public class TodoController {
    private final TodoServiceImpl todoService;

    @Autowired
    public TodoController(TodoServiceImpl todoService) {
        this.todoService = todoService;
    }

    @GetMapping
    public ResponseEntity<ResponseEnvelope<List<TodoDto>>> getTodos() {
        List<TodoDto> todoList = todoService.findTodoAll();
        return ResponseEntity.ok(new ResponseEnvelope<>(null, todoList, "TodoList fetched successfully"));
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<ResponseEnvelope<TodoDto>> getTodoById(@PathVariable Long id) {
        TodoDto findTodo = todoService.findTodoOne(id);
        return ResponseEntity.ok(new ResponseEnvelope<>(null, findTodo, "Todo fetched successfully"));
    }

    @PostMapping("/todo")
    public ResponseEntity<ResponseEnvelope<TodoDto>> saveTodo(@RequestBody TodoDto todoDto) {
        TodoDto newTodo = todoService.saveTodo(todoDto);
        return ResponseEntity.status(201).body(new ResponseEnvelope<>(null, newTodo, "Todo created successfully"));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseEnvelope<TodoDto>> updateTodo(@PathVariable Long id, @RequestBody TodoDto todoDto) {
        TodoDto updateTodo = todoService.updateTodo(id, todoDto);
        return ResponseEntity.ok(new ResponseEnvelope<>(null, updateTodo, "Todo updated successfully"));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseEnvelope<Void>> deleteTodo(@PathVariable Long id) {
        boolean deleted = todoService.deleteTodo(id);
        if (deleted) {
            return ResponseEntity.ok(new ResponseEnvelope<>(null, null, "Todo deleted successfully"));
        } else {
            return ResponseEntity.status(404).body(new ResponseEnvelope<>("404", null, "Todo not found"));
        }
    }
}