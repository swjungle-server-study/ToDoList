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

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
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

    @Test
    @DisplayName("모든 Todo 조회 성공")
    void getAllTodos() throws Exception {
        // given (준비)
        List<Todo> todoList = List.of(todo);
        given(todoService.getAllTodos()).willReturn(todoList);

        // when (실행)
        ResultActions resultActions = mockMvc.perform(get("/api/todos"));

        // then (검증)
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(responseDto.getId()))
                .andExpect(jsonPath("$[0].title").value(responseDto.getTitle()))
                .andExpect(jsonPath("$[0].content").value(responseDto.getContent()))
                .andExpect(jsonPath("$.[0].completed").value(responseDto.isCompleted())); // completed 필드 접근 수정

        verify(todoService, times(1)).getAllTodos();
    }

    @Test
    @DisplayName("특정 Todo 조회 성공")
    void getTodoById() throws Exception {
        // given (준비)
        given(todoService.getTodoById(todo.getId())).willReturn(todo);

        // when (실행)
        ResultActions resultActions = mockMvc.perform(get("/api/todos/{id}", todo.getId()));

        // then (검증)
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(todo.getId()))
                .andExpect(jsonPath("$.title").value(todo.getTitle()))
                .andExpect(jsonPath("$.content").value(todo.getContent()))
                .andExpect(jsonPath("$.completed").value(todo.isCompleted()));

        verify(todoService, times(1)).getTodoById(todo.getId());
    }

    @Test
    @DisplayName("Todo 수정 성공")
    void updateTodo() throws Exception {
        // given (준비)
        TodoRequestDto updateRequestDto = new TodoRequestDto("Updated Title", "Updated Content", true);
        Todo updatedTodo = new Todo(1L, "Updated Title", "Updated Content", true);
        TodoResponseDto updatedResponseDto = new TodoResponseDto(updatedTodo);
        given(todoService.updateTodo(eq(updatedTodo.getId()), any(TodoRequestDto.class))).willReturn(updatedResponseDto);
//        given(todoService.updateTodo(updatedTodo.getId(), updateRequestDto)).willReturn(updatedResponseDto);

        // when (실행)
        ResultActions resultActions = mockMvc.perform(
                put("/api/todos/{id}", updatedTodo.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateRequestDto))
        );

        // then (검증)
        resultActions.andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(updatedResponseDto.getId()))
                .andExpect(jsonPath("$.title").value(updatedResponseDto.getTitle()))
                .andExpect(jsonPath("$.content").value(updatedResponseDto.getContent()))
                .andExpect(jsonPath("$.completed").value(updatedResponseDto.isCompleted()));

        verify(todoService, times(1)).updateTodo(eq(updatedTodo.getId()), any(TodoRequestDto.class));
    }

    @Test
    @DisplayName("Todo 삭제 성공")
    void deleteTodo() throws Exception {
        // given (준비)
        // TodoService.deleteTodo(id)는 void 반환이므로 given 설정 불필요

        // when (실행)
        ResultActions resultActions = mockMvc.perform(delete("/api/todos/{id}", todo.getId()));

        // then (검증)
        resultActions.andExpect(status().isNoContent());
        verify(todoService).deleteTodo(todo.getId());
    }
}