package jungle.study.todo.domain;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Todo {
    @Setter
    private Long id;
    private String title;
    private String content;
    private boolean completed;

}
