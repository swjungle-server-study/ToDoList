package jungle.study.todo.repository;

import jungle.study.todo.domain.Todo;
import org.springframework.stereotype.Repository;

import java.util.concurrent.ConcurrentHashMap;

@Repository
public class TodoRepository {

    private final ConcurrentHashMap<Long, Todo> todoMap = new ConcurrentHashMap<>();

    public Todo save(Todo todo) {
        todoMap.put(todo.getId(), todo);
        return todo;
    }

}
