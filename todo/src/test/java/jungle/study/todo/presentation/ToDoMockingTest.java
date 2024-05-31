package jungle.study.todo.presentation;

import com.fasterxml.jackson.databind.ObjectMapper;
import jungle.study.todo.application.service.ToDoCommandService;
import jungle.study.todo.domain.Category;
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

import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class ToDoMockingTest {
    @InjectMocks
    ToDoController toDoController;

    @Mock
    ToDoCommandService toDoCommandService;

    MockMvc mockMvc;
    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void init(){
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
}
