package jungle.study.todo.domain;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
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
}
