package jungle.study.todo.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoRequestDto {
    private String title;
    private String content;
    private boolean completed;
}