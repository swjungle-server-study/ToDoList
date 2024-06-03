package jungle.study.todo.domain.repository;


import jungle.study.todo.domain.Category;
import jungle.study.todo.domain.ToDo;
import jungle.study.todo.domain.exception.ToDoNotFoundException;
import org.springframework.stereotype.Repository;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class ToDoInMemoryRepository {
    public AtomicInteger todoSeq = new AtomicInteger(1);
    ConcurrentHashMap<Integer, ToDo> todoDB = new ConcurrentHashMap<>();

    public ToDo createToDo(ToDo toDo) {
        Integer currentSeq = todoSeq.get();

        todoDB.put(currentSeq, toDo);

        todoSeq.incrementAndGet();
        return todoDB.get(currentSeq);
    }

    public Optional<ToDo> findByUuid(UUID uuid) {
        return todoDB.values().stream()
                .filter(toDo -> uuid.equals(toDo.getUuid()))
                .findFirst();
    }

    public List<ToDo> findAllToDo() {
        return todoDB.values().stream()
                .sorted(Comparator.comparing(todo -> todo.getToDoEssential().getPostDate(), Comparator.reverseOrder()))
                .collect(Collectors.toList());
    }

    public ToDo updateToDo(UUID uuid, String title, String contents, Category category) {
        ToDo toDo = findByUuid(uuid).orElseThrow(ToDoNotFoundException::new);

        toDo.modifyToDoEssential(title, contents, category);
        return toDo;
    }

    public void deleteToDo(UUID uuid) {
        for (Integer k : todoDB.keySet()) {
            if (todoDB.get(k).getUuid().equals(uuid)) {
                todoDB.remove(k);
                return;
            }
        }
        throw new ToDoNotFoundException();
    }
}
