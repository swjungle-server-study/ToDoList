package jungle.study.todo.application.impl;

import jungle.study.todo.application.service.ToDoQueryService;
import jungle.study.todo.domain.ToDo;
import jungle.study.todo.domain.exception.ToDoNotFoundException;
import jungle.study.todo.domain.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ToDoQueryServiceImpl implements ToDoQueryService {
    private final ToDoRepository toDoRepository;

    @Override
    public ToDo findTodoByUuid(UUID uuid) {
        return toDoRepository.findByUuid(uuid).orElseThrow(ToDoNotFoundException::new);
    }

    @Override
    public List<ToDo> findAllToDo() {
        return toDoRepository.findAll();
    }
}
