package jungle.study.todo.api.config;

import jungle.study.todo.api.domain.Todo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Configuration
public class AppConfig {
    @Bean
    public AtomicLong atomicLong() {
        return new AtomicLong(0);
    }

    @Bean
    public ConcurrentMap<Long, Todo> store() {
        return new ConcurrentHashMap<>();
    }
}
