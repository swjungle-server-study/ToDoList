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

import java.util.Collections;
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
    public ResponseEnvelope<ToDo> findToDoByUuid(@PathVariable("uuid") UUID uuid) {
        ToDo todo = toDoQueryService.findTodoByUuid(uuid);
        return ResponseEnvelope.of(todo);
    }

    @GetMapping
    public ResponseEnvelope<List<ToDo>> findAllTodos() {
        List<ToDo> todos = toDoQueryService.findAllToDo();
        return ResponseEnvelope.of(Collections.unmodifiableList(todos));
    }

    @PatchMapping
    public ResponseEnvelope<ToDo> modifyToDoEssential(@RequestBody ModifyToDoReq modifyToDoReq) {
        ToDo toDo = toDoCommandService.modifyToDoEssential(modifyToDoReq);
        return ResponseEnvelope.of(toDo);
    }

    @DeleteMapping("/{uuid}")
    public ResponseEnvelope<String> deleteToDo(@PathVariable("uuid") UUID uuid) {
        toDoCommandService.deleteToDo(uuid);
        return ResponseEnvelope.of("delete todo");
    }

    @PostMapping("/bulk")
    public ResponseEnvelope<Boolean> bulkInsertToDo(List<CreateToDoReq> createToDoReqs) {
        boolean bulkInsertResult = toDoCommandService.bulkInsertToDo(createToDoReqs);
        return ResponseEnvelope.of(bulkInsertResult);
    }
}
