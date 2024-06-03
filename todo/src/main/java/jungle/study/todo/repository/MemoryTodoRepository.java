package jungle.study.todo.repository;

import jungle.study.todo.domain.Todo;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class MemoryTodoRepository implements TodoRepository{

    private static Map<Long, Todo> store = new ConcurrentHashMap<>();
    private static AtomicLong sequence = new AtomicLong(0);

    @Override
    public Todo insertTodo(Todo todo) {
        todo.setId(sequence.incrementAndGet());
        store.put(todo.getId(), todo);
        return todo;
    }

    @Override
    public Todo updateTodo(Todo todo) {
        if (store.containsKey(todo.getId())) {
            store.put(todo.getId(), todo);
            return todo;
        } else
            return null; //여기 예외
    }

    @Override
    public boolean deleteTodo(long id) {
        if (store.containsKey(id)) {
            store.remove(id);
            return true;
        } else
            return false; //여기 예외
    }

    @Override
    public Optional<Todo> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Todo> findByTitle(String title) {
        return store.values().stream()
                .filter(todo -> todo.getTitle().contains(title))
                .findAny();
    }

    @Override
    public List<Todo> findAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
