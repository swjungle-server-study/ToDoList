package jungle.study.todo.api.controller;

import jungle.study.todo.api.constant.ResponseMessage;
import jungle.study.todo.api.domain.Todo;
import jungle.study.todo.api.dto.TodoDto;
import jungle.study.todo.api.dto.response.ResponseDto;
import jungle.study.todo.api.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/")
public class TodoController {

    private final TodoService todoService;

    @Autowired
    public TodoController(TodoService todoService) {
        this.todoService = todoService;
    }

    @PostMapping("/todo")
    public ResponseEntity<ResponseDto<Todo>> createTodo(@RequestBody TodoDto todoDto) {
        Todo todo = todoService.registerTodo(todoDto);
        ResponseDto<Todo> responseDto = new ResponseDto<>(HttpStatus.OK, todo, String.format(ResponseMessage.CREATE_TODO_SUCCESS, todo.getId()));
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/todo")
    public ResponseEntity<ResponseDto<List<Todo>>> getAllTodos() {
        List<Todo> todoList = todoService.findAll();
        ResponseDto<List<Todo>> responseDto = new ResponseDto<>(HttpStatus.OK, todoList, ResponseMessage.SUCCESS);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @GetMapping("/todo/{todoId}")
    public ResponseEntity<ResponseDto<Todo>> getTodoById(@PathVariable Long todoId) {
        Todo todo = todoService.findTodoById(todoId);
        ResponseDto<Todo> responseDto = new ResponseDto<>(HttpStatus.OK, todo, ResponseMessage.SUCCESS);
        return new ResponseEntity<>(responseDto, HttpStatus.OK);
    }

    @PutMapping("/todo/{todoId}")
    public ResponseEntity<ResponseDto> updateTodoById(@PathVariable Long todoId, @RequestBody TodoDto todoDto) {
        // 수정 로직 수정 필요
        todoService.updateTodo(todoId, todoDto);
        ResponseDto<Object> responseDto = new ResponseDto<>(HttpStatus.OK, null, ResponseMessage.SUCCESS);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }

    @DeleteMapping("/todo/{todoId}")
    public ResponseEntity<ResponseDto> deleteTodoById(@PathVariable Long todoId) {
        // 삭제 로직 수정 필요
        todoService.deleteTodo(todoId);
        ResponseDto<Object> responseDto = new ResponseDto<>(HttpStatus.OK, null, ResponseMessage.SUCCESS);
        return new ResponseEntity<ResponseDto>(responseDto, HttpStatus.OK);
    }
}
