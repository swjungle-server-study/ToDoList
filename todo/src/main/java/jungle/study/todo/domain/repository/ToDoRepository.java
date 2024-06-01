package jungle.study.todo.domain.repository;


import jungle.study.todo.domain.ToDo;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class ToDoRepository {
    public AtomicInteger todoSeq = new AtomicInteger(1);
    ConcurrentHashMap<Integer,ToDo> todoDB = new ConcurrentHashMap<>();
    public ToDo createToDo(ToDo toDo){
        Integer currentSeq = todoSeq.get();

        todoDB.put(currentSeq,toDo);

        todoSeq.incrementAndGet();
        return todoDB.get(currentSeq);
    }

    public Optional<ToDo> findByUuid(UUID uuid) {
        return todoDB.values().stream()
                .filter(toDo -> uuid.equals(toDo.getUuid()))
                .findFirst();
    }

    public List<ToDo> findAllToDo(){
        return todoDB.values().stream()
                .sorted(Comparator.comparing(todo -> todo.getToDoEssential().getPostDate(), Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }
}
