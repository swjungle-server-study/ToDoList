package jungle.study.todo.dto;

import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;

@Data
@AllArgsConstructor
public class ResponseEnvelope<T> {
    private String error;
    private T data;
    private String message;
}
