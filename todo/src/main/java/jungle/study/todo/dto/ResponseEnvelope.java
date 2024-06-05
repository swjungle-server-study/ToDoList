package jungle.study.todo.dto;

public record ResponseEnvelope<T> (
    String error,
    T data,
    String message
) {
}
