package jungle.study.todo.dto;

import jungle.study.todo.domain.Todo;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TodoRequestDto {
    private String title;
    private String content;
    private boolean completed;

    public static Todo toEntity(TodoRequestDto todoDto) {
        Todo newTodo = Todo.of(null, todoDto.getTitle(), todoDto.getContent(), todoDto.isCompleted());
        return newTodo;
    }
}