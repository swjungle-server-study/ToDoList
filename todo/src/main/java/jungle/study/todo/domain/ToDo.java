package jungle.study.todo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@NoArgsConstructor
public class ToDo {
    private UUID uuid;
    private ToDoEssential toDoEssential;

    public ToDo(UUID uuid, ToDoEssential toDoEssential) {
        this.uuid = uuid;
        this.toDoEssential = toDoEssential;
    }

    public void modifyToDoEssential(String title, String contents, Category category) {
        this.toDoEssential = new ToDoEssential(title, contents, category, this.toDoEssential.getPostDate(), this.toDoEssential.getDayOfWeek());
    }
}
