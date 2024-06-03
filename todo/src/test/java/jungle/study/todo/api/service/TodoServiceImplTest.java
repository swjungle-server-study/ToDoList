package jungle.study.todo.api.service;

import jungle.study.todo.api.domain.Todo;
import jungle.study.todo.api.domain.TodoStatus;
import jungle.study.todo.api.dto.TodoDto;
import jungle.study.todo.api.errors.errorcode.TodoErrorCode;
import jungle.study.todo.api.errors.exception.ApiException;
import jungle.study.todo.api.repository.TodoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
class TodoServiceImplTest {

    private final TodoService todoService;


    @Autowired
    public TodoServiceImplTest(TodoService todoService, TodoRepository todoRepository) {
        this.todoService = todoService;
    }

    @Test
    @DisplayName("register Todo test")
    void registerTodo() {
        TodoDto todoDto = new TodoDto("testTitle", "testContents", TodoStatus.NOT_YET);
        TodoDto titleBlankDto = new TodoDto(null, "testContents", TodoStatus.NOT_YET);
        TodoDto contentsBlankDto = new TodoDto("testTitle", null, TodoStatus.NOT_YET);

        Todo todo = todoService.registerTodo(todoDto);

        assertThat(todo.getTitle()).isEqualTo("testTitle");
        assertThat(todo.getContents()).isEqualTo("testContents");

        assertThatThrownBy(() -> todoService.registerTodo(titleBlankDto))
                .isInstanceOf(ApiException.class)
                .hasFieldOrPropertyWithValue("errorCode", TodoErrorCode.TITLE_IS_EMPTY);

        assertThatThrownBy(() -> todoService.registerTodo(contentsBlankDto))
                .isInstanceOf(ApiException.class)
                .hasFieldOrPropertyWithValue("errorCode", TodoErrorCode.CONTENTS_IS_EMPTY);

    }

    @Test
    void updateTodo() {
        TodoDto todoDto = new TodoDto("testTitle", "testContents", TodoStatus.NOT_YET);

        Todo todo = todoService.registerTodo(todoDto);

        TodoDto modifyTodoDto = new TodoDto("modifyTitle", "modifyContents", TodoStatus.DONE);

        Long isSuccess = todoService.updateTodo(todo.getId(), modifyTodoDto);

        Todo modifiedTodo = todoService.findTodoById(todo.getId());

        assertThat(isSuccess).isGreaterThan(0);
        assertThat(modifiedTodo.getId()).isEqualTo(todo.getId());
        assertThat(modifiedTodo.getTitle()).isEqualTo(modifyTodoDto.getTitle());
        assertThat(modifiedTodo.getContents()).isEqualTo(modifyTodoDto.getContents());
        assertThat(modifiedTodo.getTodoStatus()).isEqualTo(modifyTodoDto.getTodoStatus());
    }

    @Test
    void deleteTodo() {
        TodoDto todoDto = new TodoDto("testTitle", "testContents", TodoStatus.NOT_YET);

        Todo todo = todoService.registerTodo(todoDto);

        Todo findTodo = todoService.findTodoById(todo.getId());
        assertThat(findTodo).isNotNull();

        Long isSuccess = todoService.deleteTodo(findTodo.getId());
        assertThat(isSuccess).isGreaterThan(0);

        assertThatThrownBy(() -> todoService.findTodoById(todo.getId()))
                .isInstanceOf(ApiException.class)
                .hasFieldOrPropertyWithValue("errorCode", TodoErrorCode.TODO_NOT_FOUND);
    }

    @Test
    void findTodoById() {
        TodoDto todoDto = new TodoDto("testTitle", "testContents", TodoStatus.NOT_YET);

        Todo todo = todoService.registerTodo(todoDto);
        Todo findTodo = todoService.findTodoById(todo.getId());
        assertThat(findTodo.getTitle()).isEqualTo(todo.getTitle());
        assertThat(findTodo.getContents()).isEqualTo(todo.getContents());
        assertThat(findTodo.getTodoStatus()).isEqualTo(todo.getTodoStatus());

        assertThatThrownBy(() -> todoService.findTodoById(172893L))
                .isInstanceOf(ApiException.class)
                .hasFieldOrPropertyWithValue("errorCode", TodoErrorCode.TODO_NOT_FOUND);
    }

    @Test
    void findAll() {
        List<Todo> beforeTestTodo = todoService.findAll();
        if (!beforeTestTodo.isEmpty()) {
            for (Todo todo:
                 beforeTestTodo) {
                todoService.deleteTodo(todo.getId());
            }
        }



        TodoDto todoDto1 = new TodoDto("testTitle1", "testContents", TodoStatus.NOT_YET);
        Todo todo1 = todoService.registerTodo(todoDto1);
        TodoDto todoDto2 = new TodoDto("testTitle2", "testContents", TodoStatus.NOT_YET);
        Todo todo2 = todoService.registerTodo(todoDto2);
        TodoDto todoDto3 = new TodoDto("testTitle3", "testContents", TodoStatus.NOT_YET);
        Todo todo3 = todoService.registerTodo(todoDto3);

        List<Todo> allTodo = todoService.findAll();

        assertThat(allTodo.size()).isEqualTo(3);
        assertThat(todo1).isEqualTo(allTodo.get(0));
        assertThat(todo2).isEqualTo(allTodo.get(1));
        assertThat(todo3).isEqualTo(allTodo.get(2));
    }
}