package jungle.study.todo.api.dto.response;

import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

public class ResponseDto<T> {

    private final LocalDateTime time = LocalDateTime.now();
    private HttpStatus status;
    private T data;
    private String message;
}
