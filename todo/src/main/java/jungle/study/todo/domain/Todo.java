package jungle.study.todo.domain;

import jakarta.persistence.*;
import jakarta.persistence.GenerationType;
import jungle.study.todo.dto.TodoRequestDto;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity // JPA 엔티티임을 나타냄
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED) // JPA는 기본 생성자가 필요합니다.
@AllArgsConstructor
public class Todo {
    @Id // 기본 키
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 자동 증가 ID 생성 전략
    private Long id;

    @Column(nullable = false) // null 허용하지 않음
    private String title;

    @Column(nullable = false)
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