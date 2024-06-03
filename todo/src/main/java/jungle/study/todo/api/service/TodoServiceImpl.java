package jungle.study.todo.api.service;

import jungle.study.todo.api.domain.Todo;
import jungle.study.todo.api.dto.TodoDto;
import jungle.study.todo.api.errors.errorcode.TodoErrorCode;
import jungle.study.todo.api.errors.exception.ApiException;
import jungle.study.todo.api.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService{


    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo registerTodo(TodoDto todoDto) {
        String title = todoDto.getTitle();
        String contents = todoDto.getContents();

        if (title == null || title.isBlank()) {
            throw new ApiException(TodoErrorCode.TITLE_IS_EMPTY);
        }

        if (contents == null || contents.isBlank()) {
            throw new ApiException(TodoErrorCode.CONTENTS_IS_EMPTY);
        }

        return todoRepository.create(todoDto);
    }

    @Override
    public Long updateTodo(Long todoId, TodoDto todoDto) {
        Todo findTodo = todoRepository.findById(todoId);
        if (findTodo == null) {
            throw new ApiException(TodoErrorCode.TODO_NOT_FOUND);
        }

        return todoRepository.update(todoId, todoDto);
    }

    @Override
    public Long deleteTodo(Long todoId) {
        Todo findTodo = todoRepository.findById(todoId);
        if (findTodo == null) {
            throw new ApiException(TodoErrorCode.TODO_NOT_FOUND);
        }

        return todoRepository.delete(todoId);
    }

    @Override
    public Todo findTodoById(Long todoId) {
        Todo findTodo = todoRepository.findById(todoId);

        if (findTodo == null) {
            throw new ApiException(TodoErrorCode.TODO_NOT_FOUND);
        }
        return findTodo;
    }

    @Override
    public List<Todo> findAll() {
        return todoRepository.findAll();
    }
}
