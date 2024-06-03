package jungle.study.todo.service;

import jungle.study.todo.dto.TodoRequest;
import jungle.study.todo.dto.TodoResponse;
import jungle.study.todo.model.Todo;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface TodoService {
     List<TodoResponse> getAllTodos() ;
     TodoResponse getTodoById(Long id) ;
     TodoResponse createTodo(TodoRequest todoRequest);
     TodoResponse updateTodo(Long id, TodoRequest todoRequest) ;
     TodoResponse checkTodo(Long id,TodoRequest todoRequest);
     void deleteTodo(Long id) ;
     TodoResponse mapToResponse(Todo todo) ;

}
