package jungle.study.todo.api.domain;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Todo {

    private Long id;
    private String title;
    private String contents;
    private TodoStatus todoStatus;

}
