package jungle.study.todo.domain;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
public class ToDoEssential {
    private String title;
    private String contents;
    private Category category;
    private LocalDate postDate;
    private DayOfWeek dayOfWeek;
}
