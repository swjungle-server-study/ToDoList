package jungle.study.todo.domain.repository;


import jungle.study.todo.domain.ToDo;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class ToDoRepository {
    public AtomicInteger todoSeq = new AtomicInteger(1);
    ConcurrentHashMap<AtomicInteger,ToDo> todoDB = new ConcurrentHashMap<>();
    public ToDo createToDo(ToDo toDo){
        AtomicInteger currentSeq = todoSeq;
        todoDB.put(todoSeq,toDo);

        todoSeq.incrementAndGet();
        return todoDB.get(currentSeq);
    }

    public Optional<ToDo> findByUuid(UUID uuid) {
        return todoDB.values().stream()
                .filter(toDo -> uuid.equals(toDo.getUuid()))
                .findFirst();
    }
}
