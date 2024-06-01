package jungle.study.todo.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;

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
}