package jungle.study.todo.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Todo {
    private Long id;
    private String title;
    private String description;
    private boolean status;
}
