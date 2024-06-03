package jungle.study.todo.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Getter
@Entity
@Table(name = "todo")
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToDo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;
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
