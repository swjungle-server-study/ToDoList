package jungle.study.todo.service.impl;

import jungle.study.todo.domain.Todo;
import jungle.study.todo.repository.TodoRepository;
import jungle.study.todo.service.TodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Autowired
    public TodoServiceImpl(TodoRepository todoRepository) {
        this.todoRepository = todoRepository;
    }

    @Override
    public Todo save(Todo todo) {
        return todoRepository.insertTodo(todo);
    }

    @Override
    public Todo update(Long id, Todo todo) {
        todoRepository.findById(id).map(t -> {
            t.setTitle(todo.getTitle());
            t.setContents(todo.getContents());
            t.setStatus(todo.getStatus());
            return t;
        });
        return todoRepository.updateTodo(todo);
    }

    @Override
    public boolean delete(Long id) {
        return todoRepository.deleteTodo(id);
    }

    @Override
    public List<Todo> findTodo() {
        return todoRepository.findAll();
    }

    @Override
    public Optional<Todo> findTodoOne(Long TodoId) {
        return todoRepository.findById(TodoId);
    }
}
