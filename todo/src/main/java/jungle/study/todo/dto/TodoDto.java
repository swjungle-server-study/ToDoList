package jungle.study.todo.dto;

import jungle.study.todo.domain.Status;
import jungle.study.todo.domain.Todo;

public record TodoDto (
    Long id,
    String title,
    String contents,
    Status status
) {
    public TodoDto(Todo todo) {
        this(
                todo.getId(),
                todo.getTitle(),
                todo.getContents(),
                todo.getStatus()
        );
    }
}
