package jungle.study.todo.application.service;

import jungle.study.todo.presentation.dto.request.CreateToDoReq;

import java.util.UUID;

public interface ToDoCommandService {
    UUID createToDo(CreateToDoReq createToDoReq);
}
