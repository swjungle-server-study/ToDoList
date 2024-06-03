package jungle.study.todo.api.service;

import jungle.study.todo.api.domain.Todo;
import jungle.study.todo.api.dto.TodoDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TodoService {

    Todo registerTodo(TodoDto todoDto);
    Long updateTodo(Long todoId, TodoDto todoDto);
    Long deleteTodo(Long todoId);
    Todo findTodoById(Long todoId);
    List<Todo> findAll();
}
