package jungle.study.todo.application;


import jungle.study.todo.application.service.ToDoCommandService;
import jungle.study.todo.application.service.ToDoQueryService;
import jungle.study.todo.domain.Category;
import jungle.study.todo.domain.ToDo;
import jungle.study.todo.domain.ToDoEssential;
import jungle.study.todo.domain.exception.ToDoNotFoundException;
import jungle.study.todo.domain.repository.ToDoRepository;
import jungle.study.todo.presentation.dto.request.CreateToDoReq;
import jungle.study.todo.presentation.dto.request.ModifyToDoReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest
public class ToDoServiceTest {

    @Autowired
    ToDoRepository toDoRepository;

    @Autowired
    ToDoCommandService toDoCommandService;

    @Autowired
    ToDoQueryService toDoQueryService;

    @Autowired
    JdbcTemplate jdbcTemplate;

    private final Integer LOOP_SIZE = 100;

    @BeforeEach
    public void setup() {
        toDoRepository.deleteAll();
    }

    @Test
    @DisplayName("투두 단건 생성 API DB 연결")
    public void createTodoConnectionOnRepository() {
        //given
        ToDo toDo = basicToDoResponse();
        //when
        ToDo createdToDo = toDoRepository.save(toDo);

        // then
        assertNotNull(createdToDo);
    }

    @Test
    @DisplayName("투두 단건 조회 API")
    public void findTodoByUuId() {
        //given
        ToDo toDo = basicToDoResponse();
        ToDo createToDo = toDoRepository.save(toDo);
        //when
        ToDo todo = toDoQueryService.findTodoByUuid(createToDo.getUuid());
        //then
        assertThat(todo.getUuid()).isEqualTo(createToDo.getUuid());
    }

    @Test
    @DisplayName("투두 단건 조회 API Exception - ToDo 존재하지 않을 경우")
    public void findTodoByUuIdExceptionNotFound() {
        //given
        UUID uuid = UUID.randomUUID();
        //when, then
        assertThatThrownBy(() -> toDoQueryService.findTodoByUuid(uuid))
                .isInstanceOf(ToDoNotFoundException.class);
    }

    @Test
    @DisplayName("투두 전체 조회 API")
    public void findAllToDo() {
        //given
        Integer LOOP_SIZE = 5;
        IntStream.range(0, LOOP_SIZE).forEach(i -> toDoCommandService.createToDo(new CreateToDoReq("title", "contents", Category.DONE)));
        //when
        List<ToDo> todos = toDoQueryService.findAllToDo();
        //then
        assertThat(todos.size()).isEqualTo(LOOP_SIZE);
    }

    @Test
    @DisplayName("투두 필수 값 수정 API")
    public void modifyToDoEssential() {
        //given
        CreateToDoReq createToDoReq = new CreateToDoReq("테스트1", "테스트1", Category.DOING);
        UUID uuid = toDoCommandService.createToDo(createToDoReq);
        ModifyToDoReq modifyToDoReq = new ModifyToDoReq(uuid, "테스트2", "테스트2", Category.DONE);
        //when
        ToDo toDo = toDoCommandService.modifyToDoEssential(modifyToDoReq);
        //then
        assertThat(toDo.getToDoEssential().getTitle()).isEqualTo("테스트2");
    }

    @Test
    @DisplayName("투두 삭제 API")
    public void deleteToDo() {
        //given
        CreateToDoReq createToDoReq = new CreateToDoReq("test", "test", Category.DOING);
        //when
        UUID uuid = toDoCommandService.createToDo(createToDoReq);
        toDoCommandService.deleteToDo(uuid);
        //then
        assertThatThrownBy(() -> toDoQueryService.findTodoByUuid(uuid))
                .isInstanceOf(ToDoNotFoundException.class);
    }

    @Test
    @DisplayName("BulkInsert 성공")
    void bulkInsertToDoSuccess() {
        //given
        List<CreateToDoReq> createToDoReqs = new ArrayList<>();
        for (int i = 0; i < LOOP_SIZE; i++) {
            createToDoReqs.add(new CreateToDoReq("title" + i, "contents" + i, Category.DOING));
        }

        //when
        try {
            boolean bulkInsertResult = toDoCommandService.bulkInsertToDo(createToDoReqs);
            //then
            assertThat(bulkInsertResult).isTrue();
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    @Test
    @DisplayName("BulkInsert 생성결과")
    void bulkInsertToDoSize() {
        //given
        List<CreateToDoReq> createToDoReqs = new ArrayList<>();
        for (int i = 0; i < LOOP_SIZE; i++) {
            createToDoReqs.add(new CreateToDoReq("title" + i, "contents" + i, Category.DOING));
        }
        //when
        try {
            boolean bulkInsertResult = toDoCommandService.bulkInsertToDo(createToDoReqs);
            int toDoAllSize = toDoQueryService.findAllToDo().size();
            //then
            assertThat(LOOP_SIZE).isEqualTo(toDoAllSize);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }


    private ToDo basicToDoResponse() {
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
