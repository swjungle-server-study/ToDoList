package jungle.study.todo.api.dto;

import jungle.study.todo.api.domain.TodoStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TodoDto {

    private String title;
    private String contents;
    private TodoStatus todoStatus;
}
