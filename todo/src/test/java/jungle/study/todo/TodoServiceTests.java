package jungle.study.todo;

import jungle.study.todo.common.error.TodoNotFoundException;
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

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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

    @Test
    @DisplayName("모든 Todo 조회 성공")
    void getAllTodos() {
        // given (준비)
        given(todoRepository.findAll()).willReturn(List.of(todo));

        // when (실행)
        List<Todo> allTodos = todoService.getAllTodos();

        // then (검증)
        assertThat(allTodos).hasSize(1);
        assertThat(allTodos.get(0)).isEqualTo(todo);
    }

    @Test
    @DisplayName("Todo 조회 성공")
    void getTodoById() {
        // given (준비)
        given(todoRepository.findById(1L)).willReturn(Optional.of(todo));

        // when (실행)
        Todo foundTodo = todoService.getTodoById(1L);

        // then (검증)
        assertThat(foundTodo).isEqualTo(todo);
    }

    @Test
    @DisplayName("Todo 조회 실패 - TodoNotFoundException 발생")
    void getTodoById_throwsException_whenTodoNotFound() {
        given(todoRepository.findById(1L)).willReturn(Optional.empty());

        assertThatThrownBy(() -> todoService.getTodoById(1L))
                .isInstanceOf(TodoNotFoundException.class)
                .hasMessage("Todo not found with id: 1");
    }

    @Test
    @DisplayName("Todo 수정 성공")
    void updateTodo() {
        given(todoRepository.findById(1L)).willReturn(Optional.of(todo));
        given(todoRepository.save(any(Todo.class))).willReturn(todo);

        TodoResponseDto updatedTodoDto = todoService.updateTodo(1L, requestDto);

        assertThat(updatedTodoDto.getTitle()).isEqualTo("Test Title");
        verify(todoRepository, times(1)).save(any(Todo.class));
    }

    @Test
    @DisplayName("Todo 삭제 성공")
    void deleteTodo() {
        // given (준비)
        given(todoRepository.findById(1L)).willReturn(Optional.of(todo));

        // when (실행)
        todoService.deleteTodo(1L);

        // then (검증)
        verify(todoRepository, times(1)).deleteById(1L);
    }
}