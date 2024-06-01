package jungle.study.todo.application.impl;

import jungle.study.todo.application.service.ToDoCommandService;
import jungle.study.todo.domain.ToDo;
import jungle.study.todo.domain.ToDoEssential;
import jungle.study.todo.domain.exception.ToDoNotFoundException;
import jungle.study.todo.domain.repository.ToDoRepository;
import jungle.study.todo.presentation.dto.request.CreateToDoReq;
import jungle.study.todo.presentation.dto.request.ModifyToDoReq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ToDoCommandServiceImpl implements ToDoCommandService {

    private final ToDoRepository toDoRepository;

    @Override
    public UUID createToDo(CreateToDoReq createToDoReq) {
        LocalDate now = LocalDate.now();
        ToDo toDo = new ToDo(UUID.randomUUID(),
                new ToDoEssential(
                        createToDoReq.title(),
                        createToDoReq.contents(),
                        createToDoReq.category(),
                        now,
                        now.getDayOfWeek())
        );

        ToDo todo = toDoRepository.createToDo(toDo);
        if (todo == null) {
            throw new ToDoNotFoundException();
        }

        return todo.getUuid();
    }

    @Override
    public ToDo modifyToDoEssential(ModifyToDoReq modifyToDoReq) {
        return toDoRepository.updateToDo(modifyToDoReq.uuid(), modifyToDoReq.title(), modifyToDoReq.contents(), modifyToDoReq.category());
    }
}
