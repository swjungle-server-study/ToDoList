package jungle.study.todo.domain;

import jungle.study.todo.dto.TodoRequestDto;
import lombok.*;

@Getter
@AllArgsConstructor
public class Todo {
    private Long id;
    private String title;
    private String content;
    private boolean completed;

    public static Todo of(Long id, String title, String content, boolean completed) {
        return new Todo(id, title, content, completed);
    }

    public void update(TodoRequestDto todoDto) {
        this.title = todoDto.getTitle();
        this.content = todoDto.getContent();
        this.completed = todoDto.isCompleted();
    }

}