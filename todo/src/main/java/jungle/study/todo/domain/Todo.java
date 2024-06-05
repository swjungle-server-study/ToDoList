package jungle.study.todo.domain;

import jungle.study.todo.dto.TodoDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Todo {
    private Long id;
    private String title;
    private String contents;
    private Status status;

    public Todo(String title, String contents, Status status) {
        this.title = title;
        this.contents = contents;
        this.status = status;
    }

    public Todo(TodoDto todoDto) {
        this.title = todoDto.title();
        this.contents = todoDto.contents();
        this.status = todoDto.status();
    }
}
