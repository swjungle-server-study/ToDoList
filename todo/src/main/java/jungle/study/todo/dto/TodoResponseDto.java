package jungle.study.todo.dto;

import jungle.study.todo.domain.Todo;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@AllArgsConstructor
public class TodoResponseDto {
    private Long id;
    private String title;
    private String content;
    private boolean completed;

    public TodoResponseDto(Todo todo) {
        this.id = todo.getId();
        this.title = todo.getTitle();
        this.content = todo.getContent();
        this.completed = todo.isCompleted();
    }

    public static List<TodoResponseDto> valueOf(List<Todo> content) {
        return content.stream()
                .map(TodoResponseDto::new)
                .collect(Collectors.toList());
    }
}