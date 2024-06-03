package jungle.study.todo.service;

import jungle.study.todo.domain.Todo;
import java.util.List;
import java.util.Optional;

public interface TodoService {
    Todo save(Todo todo);
    Todo update(Long id, Todo todo);
    boolean delete(Long id);
    List<Todo> findTodo();
    Optional<Todo> findTodoOne(Long TodoId);
}
