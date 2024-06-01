package jungle.study.todo.application.service;

import jungle.study.todo.domain.ToDo;
import jungle.study.todo.presentation.dto.request.CreateToDoReq;
import jungle.study.todo.presentation.dto.request.ModifyToDoReq;

import java.util.UUID;

public interface ToDoCommandService {
    UUID createToDo(CreateToDoReq createToDoReq);

    ToDo modifyToDoEssential(ModifyToDoReq modifyToDoReq);
}
