package jungle.study.todo.application;


import jungle.study.todo.application.service.ToDoCommandService;
import jungle.study.todo.domain.Category;
import jungle.study.todo.domain.ToDo;
import jungle.study.todo.domain.ToDoEssential;
import jungle.study.todo.domain.repository.ToDoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class ToDoServiceTest {

    @Autowired
    ToDoRepository toDoRepository;

    @Autowired
    ToDoCommandService toDoCommandService;

    @Test
    @DisplayName("투두 단건 생성 API DB 연결")
    public void createTodoConnectionOnRepository(){
        //given
        ToDo toDo = basicToDoResponse();
        //when
        ToDo createdToDo = toDoRepository.createToDo(toDo);

        // then
        assertNotNull(createdToDo);
        assertEquals(2, toDoRepository.todoSeq.get());
    }

    private ToDo basicToDoResponse(){
        LocalDate now = LocalDate.now();
        return new ToDo(UUID.randomUUID(),
                new ToDoEssential(
                        "테스트 제목",
                        "테스트 내용",
                        Category.DOING,
                        now,
                        now.getDayOfWeek())
        );
    }
}
