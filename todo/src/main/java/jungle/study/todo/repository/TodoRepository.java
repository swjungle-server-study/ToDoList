package jungle.study.todo.repository;

import jungle.study.todo.model.Todo;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class TodoRepository {

    private List<Todo> todos = new ArrayList<>();
    private AtomicLong counter = new AtomicLong(0);

    public List<Todo> findAll() {
        return todos;
    }

    public Optional<Todo> findById(Long id) {
        return todos.stream().filter(todo -> todo.getId().equals(id)).findFirst();
    }

    public Todo save(Todo todo) {
        todo.setId(counter.incrementAndGet());
        todos.add(todo);
        return todo;
    }

    public void deleteById(Long id) {
        todos.removeIf(todo -> todo.getId().equals(id));
    }
}
