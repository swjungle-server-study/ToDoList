package jungle.study.todo.api.repository;

import jungle.study.todo.api.domain.Todo;
import jungle.study.todo.api.domain.TodoStatus;
import jungle.study.todo.api.dto.TodoDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MemoryTodoRepositoryTest {

    @Autowired
    private TodoRepository todoRepository;

    private static TodoDto todoDto;
    public int k = 0;
    @BeforeEach
    void createDto() {
        todoDto = new TodoDto("title" + k, "contents", TodoStatus.NOT_YET);
        k++;
    }


    @Test
    @DisplayName("Memory Repository Create Test")
    void create() {

        Todo createTodo = todoRepository.create(todoDto);

        assertEquals(1L, createTodo.getId());
        assertEquals(todoDto.getTitle(), createTodo.getTitle());
        assertEquals(todoDto.getContents(), createTodo.getContents());
        assertEquals(todoDto.getTodoStatus(), createTodo.getTodoStatus());

    }

    @Test
    @DisplayName("Memory Repository findById Test")
    void findById() {
        Todo createTodo = todoRepository.create(todoDto);
        Todo findTodo = todoRepository.findById(createTodo.getId());
        assertEquals(createTodo, findTodo);
    }


    @Test
    void findAll() {

        List<Todo> allTodo = todoRepository.findAll();
        Todo findTodo = allTodo.get(0);

        assertEquals(k, allTodo.size());
        assertEquals(todoDto.getTitle(), findTodo.getTitle());
        assertEquals(todoDto.getContents(), findTodo.getContents());
        assertEquals(todoDto.getTodoStatus(), findTodo.getTodoStatus());

    }

    @Test
    void update() {
        Todo createTodo = todoRepository.create(todoDto);

        String updateTitle = "upTitle";
        String updateContents = "upContents";
        TodoDto updateTodoDto = new TodoDto(updateTitle, updateContents, TodoStatus.DONE);
        Long updatedId = todoRepository.update(createTodo.getId(), updateTodoDto);

        Todo findTodo = todoRepository.findById(updatedId);

        assertEquals(createTodo.getId(), updatedId);
        assertNotEquals(createTodo.getTitle(), findTodo.getTitle());
        assertNotEquals(createTodo.getContents(), findTodo.getContents());
        assertEquals(updateTitle, findTodo.getTitle());
        assertEquals(updateContents, findTodo.getContents());
    }

    @Test
    void delete() {
        Todo createTodo = todoRepository.create(todoDto);
        Long deletedId = todoRepository.delete(createTodo.getId());
        Todo findTodo = todoRepository.findById(deletedId);
        assertEquals(deletedId, createTodo.getId());
        assertNull(findTodo);

    }
}