package jungle.study.todo.application.service;

import jungle.study.todo.domain.ToDo;
import jungle.study.todo.presentation.dto.request.TodoDetailReq;

import java.util.UUID;

public interface ToDoQueryService {
    ToDo findTodoByUuid(TodoDetailReq todoDetailReq);
}
