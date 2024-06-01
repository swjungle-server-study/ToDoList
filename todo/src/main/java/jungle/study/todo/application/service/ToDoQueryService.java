package jungle.study.todo.application.service;

import jungle.study.todo.domain.ToDo;

import java.util.UUID;

public interface ToDoQueryService {
    ToDo findTodoByUuid(UUID uuid);
}
