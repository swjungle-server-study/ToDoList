package jungle.study.todo.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoResponse {
    private Long id;
    private String title;
    private String description;
    private boolean checked;
}
