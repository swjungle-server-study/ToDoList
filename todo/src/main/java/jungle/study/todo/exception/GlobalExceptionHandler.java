package jungle.study.todo.exception;

import jungle.study.todo.dto.Envelope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<Envelope<String>> handleTodoNotFoundException(TodoNotFoundException ex) {
        return new ResponseEntity<>(new Envelope<>(ex.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Envelope<String>> handleException(Exception ex) {
        return new ResponseEntity<>(new Envelope<>(ex.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}