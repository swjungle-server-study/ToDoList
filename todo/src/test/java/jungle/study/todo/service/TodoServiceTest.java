package jungle.study.todo.service;

import jungle.study.todo.domain.Status;
import jungle.study.todo.domain.Todo;
import jungle.study.todo.dto.TodoDto;
import jungle.study.todo.exception.ApiException;
import jungle.study.todo.repository.MemoryTodoRepository;
import jungle.study.todo.service.impl.TodoServiceImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class TodoServiceTest {

    TodoServiceImpl todoService;
    MemoryTodoRepository todoRepository;

    @BeforeEach
    public void beforeEach() {
        todoRepository = new MemoryTodoRepository();
        todoService = new TodoServiceImpl(todoRepository);
    }

    @AfterEach
    public void afterEach() {
        todoRepository.clearStore();
    }

    @Test
    void saveTodo() {
        //given
        Todo todo = new Todo("title", "contents", Status.TODO);

        //when
        TodoDto saveTodoDto = todoService.saveTodo(new TodoDto(todo));
        TodoDto findTodoDto = todoService.findTodoOne(saveTodoDto.id());

        //then
        Assertions.assertEquals(saveTodoDto, findTodoDto);
    }

    @Test
    void updateTodo() {
        Todo todo = new Todo("title", "contents", Status.TODO);
        TodoDto saveTodoDto = todoService.saveTodo(new TodoDto(todo));

        todo.setContents("update contents");
        TodoDto updateTodoDto = todoService.updateTodo(saveTodoDto.id(), new TodoDto(todo));

        Assertions.assertEquals("update contents", updateTodoDto.contents());
    }

    @Test
    void deleteTodo() {
        Todo todo = new Todo("title", "contents", Status.TODO);
        TodoDto saveTodoDto = todoService.saveTodo(new TodoDto(todo));

        todoService.deleteTodo(saveTodoDto.id());

        Assertions.assertThrows(ApiException.class, () -> {
            todoService.findTodoOne(saveTodoDto.id());
        });
    }

    @Test
    void findTodoAll() {
        Todo todo1 = new Todo("title1", "contents1", Status.TODO);
        TodoDto saveTodoDto1 = todoService.saveTodo(new TodoDto(todo1));

        Todo todo2 = new Todo("title2", "contents2", Status.DONE);
        TodoDto saveTodoDto2 = todoService.saveTodo(new TodoDto(todo2));

        List<TodoDto> result = todoService.findTodoAll();

        Assertions.assertEquals(result.size(), 2);
    }

    @Test
    void findTodoOne() {
        Todo todo = new Todo("title", "contents", Status.TODO);
        TodoDto saveTodoDto = todoService.saveTodo(new TodoDto(todo));

        TodoDto findTodoDto = todoService.findTodoOne(saveTodoDto.id());

        Assertions.assertEquals(saveTodoDto, findTodoDto);
    }
}