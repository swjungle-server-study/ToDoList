package jungle.study.todo.domain;

import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ToDoEssential {
    private String title;
    private String contents;
    private Category category;
    private LocalDate postDate;
    private DayOfWeek dayOfWeek;

    public ToDoEssential(String title, String contents, Category category, LocalDate postDate, DayOfWeek dayOfWeek) {
        this.title = title;
        this.contents = contents;
        this.category = category;
        this.postDate = postDate;
        this.dayOfWeek = dayOfWeek;
    }
}
