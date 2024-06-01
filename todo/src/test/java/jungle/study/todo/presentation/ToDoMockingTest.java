package jungle.study.todo.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jungle.study.todo.application.service.ToDoCommandService;
import jungle.study.todo.application.service.ToDoQueryService;
import jungle.study.todo.domain.Category;
import jungle.study.todo.domain.ToDo;
import jungle.study.todo.domain.ToDoEssential;
import jungle.study.todo.presentation.dto.request.CreateToDoReq;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.UUID;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ToDoMockingTest {
    @InjectMocks
    ToDoController toDoController;

    @Mock
    ToDoCommandService toDoCommandService;

    @Mock
    ToDoQueryService toDoQueryService;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init() {
        mockMvc = MockMvcBuilders.standaloneSetup(toDoController).build();
    }

    @Test
    @DisplayName("투두 단건 생성 API Mocking 테스트")
    public void createToDoTestWithMock() throws Exception {
        //given
        CreateToDoReq createToDoReq = new CreateToDoReq("테스트 제목", "테스트 내용", Category.DOING);
        //when, then
        mockMvc.perform(
                        post("/todos")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(createToDoReq)))
                .andExpect(status().isCreated());

        verify(toDoCommandService).createToDo(createToDoReq);
    }

    @Test
    @DisplayName("투두 단건 조회 API Mocking 테스트")
    public void findTodoByUuIdWithMock() throws Exception {
        //given
        UUID uuid = UUID.randomUUID(); // 임의의 UUID 생성
        ToDo toDo = new ToDo(uuid, new ToDoEssential("테스트 제목", "테스트 내용", Category.DOING, LocalDate.now(), DayOfWeek.MONDAY));

        when(toDoQueryService.findTodoByUuid(uuid)).thenReturn(toDo);

        //when, then
        mockMvc.perform(get("/todos/" + uuid))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.data.uuid").value(uuid.toString())) // UUID를 문자열로 비교
                .andExpect(jsonPath("$.data.toDoEssential.title").value("테스트 제목")); // 반환된 ToDo 객체의 제목이 올바른지 검증

    }
}
