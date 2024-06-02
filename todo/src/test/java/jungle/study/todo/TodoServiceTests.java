package jungle.study.todo;

import jungle.study.todo.domain.Todo;
import jungle.study.todo.dto.TodoRequestDto;
import jungle.study.todo.dto.TodoResponseDto;
import jungle.study.todo.repository.TodoRepository;
import jungle.study.todo.service.TodoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @InjectMocks
    private TodoService todoService;

    @Mock
    private TodoRepository todoRepository;

    private TodoRequestDto requestDto;
    private TodoResponseDto responseDto;
    private Todo todo;

    @BeforeEach
    void setUp() {
        todo = new Todo(1L, "Test Title", "Test Content", false);
        requestDto = new TodoRequestDto("Test Title", "Test Content", false);
        responseDto = new TodoResponseDto(todo);
    }

    @Test
    @DisplayName("Todo 생성 성공")
    void createTodo() {
        // given (준비)
        given(todoRepository.save(any(Todo.class))).willReturn(todo);

        // when (실행)
        TodoResponseDto createdTodoDto = todoService.createTodo(requestDto);

        // then (검증)
        assertThat(createdTodoDto.getId()).isEqualTo(1L);
        assertThat(createdTodoDto.getTitle()).isEqualTo("Test Title");
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

}
