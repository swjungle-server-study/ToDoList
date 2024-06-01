package jungle.study.todo.application.impl;

import jungle.study.todo.application.service.ToDoQueryService;
import jungle.study.todo.domain.ToDo;
import jungle.study.todo.domain.exception.ToDoNotFoundException;
import jungle.study.todo.domain.repository.ToDoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ToDoQueryServiceImpl implements ToDoQueryService {
    private final ToDoRepository toDoRepository;

    @Override
    public ToDo findTodoByUuid(UUID uuid) {
        ToDo todo = toDoRepository.findByUuid(uuid).orElseThrow(ToDoNotFoundException::new);
        return todo;
    }
}
