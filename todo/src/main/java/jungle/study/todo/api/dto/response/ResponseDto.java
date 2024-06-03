package jungle.study.todo.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ResponseDto<T> {

    private final LocalDateTime time = LocalDateTime.now();
    private HttpStatus status;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T data;
    private String message;
}
