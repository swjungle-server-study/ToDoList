package jungle.study.todo.repository;

import jungle.study.todo.domain.Todo;

import java.util.List;
import java.util.Optional;

public interface TodoRepository {
    Todo insertTodo(Todo todo);
    Todo updateTodo(Todo todo);
    boolean deleteTodo(long id);
    Optional<Todo> findById(Long id);
    Optional<Todo> findByTitle(String name);
    List<Todo> findAll();
}
