package jungle.study.todo.repository;

import jungle.study.todo.domain.Todo;
import jungle.study.todo.exception.ApiException;
import jungle.study.todo.exception.ErrorCode;

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
            throw new ApiException(ErrorCode.TODO_NOT_FOUND);
    }

    @Override
    public boolean deleteTodo(long id) {
        if (store.containsKey(id)) {
            store.remove(id);
            return true;
        } else
            throw new ApiException(ErrorCode.TODO_NOT_FOUND);
    }

    @Override
    public Optional<Todo> findTodoById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public Optional<Todo> findTodoByTitle(String title) {
        return store.values().stream()
                .filter(todo -> todo.getTitle().contains(title))
                .findAny();
    }

    @Override
    public List<Todo> findTodoAll() {
        return new ArrayList<>(store.values());
    }

    public void clearStore() {
        store.clear();
    }
}
