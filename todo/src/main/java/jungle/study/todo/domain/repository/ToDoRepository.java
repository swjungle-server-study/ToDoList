package jungle.study.todo.domain.repository;

import jungle.study.todo.domain.ToDo;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ToDoRepository extends Repository<ToDo, Long> {
    ToDo save(ToDo toDo);

    Optional<ToDo> findByUuid(UUID uuid);

    List<ToDo> findAll();

    void delete(ToDo toDo);

    void deleteAll();
}
