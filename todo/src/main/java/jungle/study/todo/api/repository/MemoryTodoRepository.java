package jungle.study.todo.api.repository;

import jungle.study.todo.api.domain.Todo;
import jungle.study.todo.api.dto.TodoDto;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

import static jungle.study.todo.api.constant.RepositoryConst.*;

@Repository
public class MemoryTodoRepository implements TodoRepository{

    private final ConcurrentMap<Long, Todo> store;
    private final AtomicLong atomicLong;

    public MemoryTodoRepository(AtomicLong atomicLong, ConcurrentMap<Long, Todo> store) {
        this.atomicLong = atomicLong;
        this.store = store;
    }

    @Override
    public Todo create(TodoDto todoDto) {
        Long id = atomicLong.incrementAndGet();
        Todo todo = Todo.builder().
                id(id).
                title(todoDto.getTitle())
                .contents(todoDto.getContents())
                .todoStatus(todoDto.getTodoStatus())
                .build();
        store.put(id, todo);

        return todo;
    }

    @Override
    public Todo findById(Long todoId) {

        return store.get(todoId);
    }


    @Override
    public List<Todo> findAll() {
        return new ArrayList<>(store.values());
    }

    @Override
    public Long update(Long todoId, TodoDto todoDto) {
        Todo findTodo = store.get(todoId);

        if (findTodo == null)
            return REPOSITORY_FAIL;

        Todo updatedTodo = Todo.builder()
                .id(todoId)
                .title(todoDto.getTitle())
                .contents(todoDto.getContents())
                .todoStatus(todoDto.getTodoStatus())
                .build();
        store.put(todoId, updatedTodo);

        return todoId;
    }

    @Override
    public Long delete(Long todoId) {
        Todo findTodo = store.get(todoId);

        if (findTodo == null)
            return REPOSITORY_FAIL;

        store.remove(todoId);

        return todoId;
    }
}
