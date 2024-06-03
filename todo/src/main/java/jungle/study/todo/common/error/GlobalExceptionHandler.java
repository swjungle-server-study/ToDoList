package jungle.study.todo.common.error;

import jungle.study.todo.dto.EnvelopeResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.NoSuchElementException;

@RestControllerAdvice // 모든 @RestController에 대한 예외 처리
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<EnvelopeResponseDto<String>> handleException(Exception ex) {
        return ResponseEntity.status(CommonErrorCode.INTERNAL_SERVER_ERROR.getHttpStatus())
                .body(EnvelopeResponseDto.error(CommonErrorCode.INTERNAL_SERVER_ERROR));
    }

    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<EnvelopeResponseDto<String>> handleNoSuchElementException(NoSuchElementException ex) {
        return ResponseEntity.status(CommonErrorCode.TODO_NOT_FOUND.getHttpStatus())
                .body(EnvelopeResponseDto.error(CommonErrorCode.TODO_NOT_FOUND));
    }
}