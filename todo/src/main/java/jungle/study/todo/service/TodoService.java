package jungle.study.todo.service;

import jungle.study.todo.domain.Todo;
import jungle.study.todo.dto.TodoRequestDto;
import jungle.study.todo.dto.TodoResponseDto;
import jungle.study.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.concurrent.atomic.AtomicLong;

@Service
@RequiredArgsConstructor
public class TodoService {

    private final TodoRepository todoRepository;
    private final AtomicLong sequence = new AtomicLong(1);

    public TodoResponseDto createTodo(TodoRequestDto todoDto) {
        long newId = sequence.getAndIncrement();
        Todo newTodo = Todo.of(newId, todoDto.getTitle(), todoDto.getContent(), todoDto.isCompleted());
        return new TodoResponseDto(todoRepository.save(newTodo));
    }

}