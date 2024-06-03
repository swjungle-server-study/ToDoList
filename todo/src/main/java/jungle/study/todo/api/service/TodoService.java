package jungle.study.todo.api.service;

import jungle.study.todo.api.domain.Todo;
import jungle.study.todo.api.dto.TodoDto;

import java.util.List;

public interface TodoService {

    Todo registerTodo(TodoDto todoDto);
    int updateTodo(Long todoId);
    int deleteTodo(Long todoId);
    Todo findTodoById(Long todoId);
    List<Todo> findAll();
}
