package jungle.study.todo.service;

import jungle.study.todo.domain.Status;
import jungle.study.todo.domain.Todo;
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
    void save() {
        //given
        Todo todo = new Todo("title", "contents", Status.TODO);

        //when
        long saveId = todoService.save(todo);
        Todo findTodo = todoService.findTodoOne(saveId).get();

        //then
        Assertions.assertEquals(todo, findTodo);
    }

//    @Test
//    void 중복_회원_예외() {
//        //given
//        Todo todo1 = new Todo();
//        todo1.setName("spring");
//
//        Todo todo2 = new Todo();
//        todo2.setName("spring");
//
//        //when
//        todoService.join(todo1);
////        try {
////            todoService.join(todo2);
////            fail("예외가 발생해야 합니다.");
////        } catch (IllegalStateException e) {
////            Assertions.assertEquals(e.getMessage(), "이미 존재하는 회원입니다.");
////        }
//        IllegalStateException e = assertThrows(IllegalStateException.class, () -> todoService.join(todo2));
//        Assertions.assertEquals(e.getMessage(), "이미 존재하는 회원입니다.");
//
//        //then
//    }

    @Test
    void update() {
        Todo todo = new Todo("title", "contents", Status.TODO);
        todoService.save(todo);

        todo.setContents("update contents");
        todoService.update(todo);

        Todo findTodo = todoService.findTodoOne(todo.getId()).get();

        Assertions.assertEquals(todo.getContents(), findTodo.getContents());
    }

    @Test
    void delete() {
        Todo todo = new Todo("title", "contents", Status.TODO);
        todoService.save(todo);

        todoService.delete(todo);

        Assertions.assertTrue(todoService.findTodoOne(todo.getId()).isEmpty());
    }

    @Test
    void findTodo() {
        Todo todo1 = new Todo("title1", "contents1", Status.TODO);
        todoService.save(todo1);

        Todo todo2 = new Todo("title2", "contents2", Status.DONE);
        todoService.save(todo2);

        List<Todo> result = todoService.findTodo();

        Assertions.assertEquals(result.size(), 2);
    }

    @Test
    void findTodoOne() {
    }
}