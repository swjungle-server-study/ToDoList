package jungle.study.todo.api.repository;

import jungle.study.todo.api.domain.Todo;
import jungle.study.todo.api.dto.TodoDto;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository {

    Todo create(TodoDto todoDto);
    Todo findById(Long todoId);
    List<Todo> findAll();
    Long update(Long todoId, TodoDto todoDto);
    Long delete(Long todoId);
}
