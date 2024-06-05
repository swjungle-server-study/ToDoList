package jungle.study.todo.service.impl;

import jungle.study.todo.domain.Todo;
import jungle.study.todo.dto.TodoDto;
import jungle.study.todo.exception.ApiException;
import jungle.study.todo.exception.ErrorCode;
import jungle.study.todo.repository.TodoRepository;
import jungle.study.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public TodoDto saveTodo(TodoDto todoDto) {
        Todo todo = todoRepository.insertTodo(new Todo(todoDto));
        return new TodoDto(todo);
    }

    @Override
    public TodoDto updateTodo(Long id, TodoDto todoDto) {
        Todo todo = todoRepository.findTodoById(id).map(t -> {
            t.setTitle(todoDto.title());
            t.setContents(todoDto.contents());
            t.setStatus(todoDto.status());
            return t;})
                .orElseThrow(() -> new ApiException(ErrorCode.TODO_NOT_FOUND));
        todoRepository.updateTodo(todo);
        return new TodoDto(todo);
    }

    @Override
    public boolean deleteTodo(Long id) {
        return todoRepository.deleteTodo(id);
    }

    @Override
    public List<TodoDto> findTodoAll() {
        List<TodoDto> list = new ArrayList<>();

        List<Todo> todoList = todoRepository.findTodoAll();
        for (Todo todo : todoList) {
            list.add(new TodoDto(todo));
        }
        return list;
    }

    @Override
    public TodoDto findTodoOne(Long id) {
        Todo todo = todoRepository.findTodoById(id)
                .orElseThrow(() -> new ApiException(ErrorCode.TODO_NOT_FOUND));
        return new TodoDto(todo);
    }
}
