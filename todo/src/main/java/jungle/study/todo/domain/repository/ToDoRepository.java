package jungle.study.todo.domain.repository;

import jungle.study.todo.domain.ToDo;
import org.springframework.data.repository.Repository;

public interface ToDoRepository extends Repository<ToDo, Long> {
}
