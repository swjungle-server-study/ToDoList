package jungle.study.todo.presentation;

import jungle.study.todo.application.service.ToDoCommandService;
import jungle.study.todo.application.service.ToDoQueryService;
import jungle.study.todo.domain.ToDo;
import jungle.study.todo.global.dto.ResponseEnvelope;
import jungle.study.todo.presentation.dto.request.CreateToDoReq;
import jungle.study.todo.presentation.dto.request.ModifyToDoReq;
import jungle.study.todo.presentation.dto.response.ToDoUuidRes;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("todos")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoCommandService toDoCommandService;
    private final ToDoQueryService toDoQueryService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEnvelope<?> createToDo(@RequestBody CreateToDoReq createToDoReq) {
        UUID uuid = toDoCommandService.createToDo(createToDoReq);
        return ResponseEnvelope.of(new ToDoUuidRes(uuid));
    }

    @GetMapping("/{uuid}")
    public ResponseEnvelope<?> findToDoByUuid(@PathVariable("uuid") UUID uuid) {
        ToDo todo = toDoQueryService.findTodoByUuid(uuid);
        return ResponseEnvelope.of(todo);
    }

    @GetMapping
    public ResponseEnvelope<?> findAllTodos() {
        List<ToDo> todos = toDoQueryService.findAllToDo();
        return ResponseEnvelope.of(todos);
    }

    @PatchMapping
    public ResponseEnvelope<?> modifyToDoEssential(@RequestBody ModifyToDoReq modifyToDoReq) {
        ToDo toDo = toDoCommandService.modifyToDoEssential(modifyToDoReq);
        return ResponseEnvelope.of(toDo);
    }
}
