package jungle.study.todo;

import jungle.study.todo.repository.MemoryTodoRepository;
import jungle.study.todo.repository.TodoRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SpringConfig {

    @Bean
    public TodoRepository todoRepository() {
        return new MemoryTodoRepository();
    }
}
