package jungle.study.todo.service;

import jungle.study.todo.dto.TodoDto;

import java.util.List;

public interface TodoService {
    TodoDto saveTodo(TodoDto todo);
    TodoDto updateTodo(Long id, TodoDto todo);
    boolean deleteTodo(Long id);
    List<TodoDto> findTodoAll();
    TodoDto findTodoOne(Long id);
}
