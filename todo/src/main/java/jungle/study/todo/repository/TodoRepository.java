package jungle.study.todo.repository;

import jungle.study.todo.domain.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    Todo insertTodo(Todo todo);
    Todo updateTodo(Todo todo);
    boolean deleteTodo(long id);
    Optional<Todo> findTodoById(Long id);
    Optional<Todo> findTodoByTitle(String name);
    List<Todo> findTodoAll();
}
