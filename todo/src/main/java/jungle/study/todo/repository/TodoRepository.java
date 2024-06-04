package jungle.study.todo.repository;

import jungle.study.todo.domain.Todo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TodoRepository {

    private final ConcurrentHashMap<Long, Todo> todoMap = new ConcurrentHashMap<>();

    public Todo save(Todo todo) {
        todoMap.put(todo.getId(), todo);
        return todo;
    }

    public List<Todo> findAll() {
        return new ArrayList<>(todoMap.values());
    }

    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(todoMap.get(id));
    }

    public void update(Long id, Todo updatedTodo) {
        todoMap.put(id, updatedTodo);
    }

    public void deleteById(Long id) {
        todoMap.remove(id);
    }
}
