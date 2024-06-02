package jungle.study.todo;

import com.fasterxml.jackson.databind.ObjectMapper;
import jungle.study.todo.controller.TodoController;
import jungle.study.todo.domain.Todo;
import jungle.study.todo.dto.TodoRequestDto;
import jungle.study.todo.dto.TodoResponseDto;
import jungle.study.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(TodoController.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TodoService todoService;

    private Todo todo;
    private TodoRequestDto requestDto;
    private TodoResponseDto responseDto;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        todo = new Todo(1L, "Test Title", "Test Content", false);
        requestDto = new TodoRequestDto("Test Title", "Test Content", false);
        responseDto = new TodoResponseDto(todo);
        objectMapper = new ObjectMapper();
    }

    @Test
    @DisplayName("Todo 생성 성공")
    void createTodo() throws Exception {
        // given (준비)
        given(todoService.createTodo(any(TodoRequestDto.class))).willReturn(responseDto);
//        given(todoService.createTodo(requestDto)).willReturn(responseDto);

        // when (실행)
        ResultActions resultActions = mockMvc.perform(
                post("/api/todos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDto)));

        // then (검증)
        resultActions.andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(responseDto.getId()))
                .andExpect(jsonPath("$.title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$.content").value(responseDto.getContent()))
                .andExpect(jsonPath("$.completed").value(responseDto.isCompleted()));

        verify(todoService, times(1)).createTodo(any(TodoRequestDto.class));
    }

}
