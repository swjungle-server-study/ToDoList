package jungle.study.todo.controller;

import jungle.study.todo.dto.EnvelopeResponseDto;
import jungle.study.todo.dto.TodoRequestDto;
import jungle.study.todo.dto.TodoResponseDto;
import jungle.study.todo.service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/todos")
public class TodoController {

    private final TodoService todoService;

    @PostMapping
    public ResponseEntity<EnvelopeResponseDto<TodoResponseDto>> createTodo(@RequestBody final TodoRequestDto requestDto) {
        TodoResponseDto createdTodoDto = todoService.createTodo(requestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(EnvelopeResponseDto.success(createdTodoDto));
    }
    @GetMapping
    public ResponseEntity<EnvelopeResponseDto<List<TodoResponseDto>>> getAllTodos() {
        List<TodoResponseDto> todoResponseDtos = TodoResponseDto.valueOf(new ArrayList<>(todoService.getAllTodos()));
        return ResponseEntity.ok().body(EnvelopeResponseDto.success(todoResponseDtos));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EnvelopeResponseDto<TodoResponseDto>> getTodo(@PathVariable Long id) {
        TodoResponseDto todoResponseDto = new TodoResponseDto(todoService.getTodoById(id));
        return ResponseEntity.ok().body(EnvelopeResponseDto.success(todoResponseDto));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EnvelopeResponseDto<TodoResponseDto>> updateTodo(@PathVariable Long id, @RequestBody TodoRequestDto requestDto) {
        TodoResponseDto updatedTodo = todoService.updateTodo(id, requestDto);
        return ResponseEntity.ok().body(EnvelopeResponseDto.success(updatedTodo));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<EnvelopeResponseDto<String>> deleteTodo(@PathVariable Long id) {
        todoService.deleteTodo(id);
        return ResponseEntity.ok().body(EnvelopeResponseDto.success("TODO 삭제 성공"));
    }
}
