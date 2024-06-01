package jungle.study.todo.application;


import jungle.study.todo.application.service.ToDoCommandService;
import jungle.study.todo.application.service.ToDoQueryService;
import jungle.study.todo.domain.Category;
import jungle.study.todo.domain.ToDo;
import jungle.study.todo.domain.ToDoEssential;
import jungle.study.todo.domain.exception.ToDoNotFoundException;
import jungle.study.todo.domain.repository.ToDoRepository;
import jungle.study.todo.presentation.dto.request.CreateToDoReq;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class ToDoServiceTest {

    @Autowired
    ToDoRepository toDoRepository;

    @Autowired
    ToDoCommandService toDoCommandService;

    @Autowired
    ToDoQueryService toDoQueryService;

    String NOT_FOUND = "투두가 존재하지 않습니다";

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

    @Test
    @DisplayName("투두 단건 조회 API")
    public void findTodoByUuId(){
        //given
        ToDo toDo = basicToDoResponse();
        ToDo createToDo = toDoRepository.createToDo(toDo);
        //when
        ToDo todo = toDoQueryService.findTodoByUuid(createToDo.getUuid());
        //then
        assertThat(todo.getUuid()).isEqualTo(createToDo.getUuid());
    }

    @Test
    @DisplayName("투두 단건 조회 API Exception - ToDo 존재하지 않을 경우")
    public void findTodoByUuIdExceptionNotFound(){
        //given
        UUID uuid = UUID.randomUUID();
        //when, then
        assertThatThrownBy(()->toDoQueryService.findTodoByUuid(uuid))
                .isInstanceOf(ToDoNotFoundException.class);
    }

    @Test
    @DisplayName("투두 전체 조회 API")
    public void findAllToDo(){
        //given
        Integer LOOP_SIZE = 5;
        IntStream.range(0,LOOP_SIZE).forEach(i->toDoCommandService.createToDo(new CreateToDoReq("title","contents",Category.DONE)));
        //when
        List<ToDo> todos = toDoQueryService.findAllToDo();
        //then
        assertThat(todos.size()).isEqualTo(LOOP_SIZE);
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
