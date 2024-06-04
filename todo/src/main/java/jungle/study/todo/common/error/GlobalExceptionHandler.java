package jungle.study.todo.common.error;

import jungle.study.todo.dto.EnvelopeResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice // 모든 @RestController에 대한 예외 처리
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EnvelopeResponseDto<String>> handleException(Exception ex) {
        return ResponseEntity.status(CommonErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(EnvelopeResponseDto.error(CommonErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(TodoNotFoundException.class)
    public ResponseEntity<EnvelopeResponseDto<String>> handleTodoNotFoundException(TodoNotFoundException ex) {
        return ResponseEntity.status(CommonErrorCode.TODO_NOT_FOUND.getHttpStatus())
                .body(EnvelopeResponseDto.error(CommonErrorCode.TODO_NOT_FOUND));
    }
}