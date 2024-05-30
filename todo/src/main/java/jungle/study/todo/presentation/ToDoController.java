package jungle.study.todo.presentation;

import jungle.study.todo.application.service.ToDoCommandService;
import jungle.study.todo.global.dto.ResponseEnvelope;
import jungle.study.todo.presentation.dto.request.CreateToDoReq;
import jungle.study.todo.presentation.dto.response.ToDoUuidRes;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("todos")
@RequiredArgsConstructor
public class ToDoController {
    private final ToDoCommandService toDoCommandService;
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEnvelope<?> createToDo(@RequestBody CreateToDoReq createToDoReq){
        UUID uuid = toDoCommandService.createToDo(createToDoReq);
        return ResponseEnvelope.of(new ToDoUuidRes(uuid));
    }
}
