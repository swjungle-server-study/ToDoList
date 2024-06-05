package jungle.study.todo.repository;

import jungle.study.todo.domain.Status;
import jungle.study.todo.domain.Todo;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;

class MemoryTodoRepositoryTest {
    MemoryTodoRepository repository = new MemoryTodoRepository();

    @AfterEach
    public void afterEach() {
        repository.clearStore();
    }

    @Test
    public void insertTodo() {
        Todo todo = new Todo("title", "contents", Status.TODO);

        repository.insertTodo(todo);

        Todo result = repository.findTodoById(todo.getId()).get();
        Assertions.assertEquals(todo, result);
    }

    @Test
    public void updateTodo() {
        Todo todo = new Todo("title", "contents", Status.TODO);
        repository.insertTodo(todo);

        todo.setTitle("update title");
        todo.setStatus(Status.DONE);
        repository.updateTodo(todo);

        Todo result = repository.findTodoById(todo.getId()).get();
        Assertions.assertEquals(todo.getTitle(), result.getTitle());
        Assertions.assertEquals(todo.getStatus(), result.getStatus());
    }

    @Test
    public void deleteTodo() {
        Todo todo = new Todo("title", "contents", Status.TODO);
        repository.insertTodo(todo);

        repository.deleteTodo(todo.getId());

        Assertions.assertTrue(repository.findTodoById(todo.getId()).isEmpty());
    }

    @Test
    public void findTodoById() {
        Todo todo1 = new Todo("title1", "contents1", Status.TODO);
        repository.insertTodo(todo1);

        Todo todo2 = new Todo("title2", "contents2", Status.DONE);
        repository.insertTodo(todo2);

        Todo result = repository.findTodoById(todo1.getId()).get();

        Assertions.assertEquals(todo1, result);
    }

    @Test
    public void findByTitle() {
        Todo todo1 = new Todo("title1", "contents1", Status.TODO);
        repository.insertTodo(todo1);

        Todo todo2 = new Todo("title2", "contents2", Status.DONE);
        repository.insertTodo(todo2);

        Todo result = repository.findTodoByTitle("title1").get();

        Assertions.assertEquals(todo1, result);
    }

    @Test
    public void findAll() {
        Todo todo1 = new Todo("title1", "contents1", Status.TODO);
        repository.insertTodo(todo1);

        Todo todo2 = new Todo("title2", "contents2", Status.DONE);
        repository.insertTodo(todo2);

        List<Todo> result = repository.findTodoAll();

        Assertions.assertEquals(result.size(), 2);
    }
}
