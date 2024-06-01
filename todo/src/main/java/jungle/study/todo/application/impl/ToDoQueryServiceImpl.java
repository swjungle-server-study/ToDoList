package jungle.study.todo.application.impl;

import jungle.study.todo.application.service.ToDoQueryService;
import jungle.study.todo.domain.ToDo;
import jungle.study.todo.domain.exception.ToDoNotFoundException;
import jungle.study.todo.domain.repository.ToDoRepository;
import jungle.study.todo.presentation.dto.request.TodoDetailReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ToDoQueryServiceImpl implements ToDoQueryService {
    private final ToDoRepository toDoRepository;

    @Override
    public ToDo findTodoByUuid(TodoDetailReq todoDetailReq) {
        ToDo todo = toDoRepository.findByUuid(todoDetailReq.uuid()).orElseThrow(ToDoNotFoundException::new);
        return todo;
    }
}
