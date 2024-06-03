package jungle.study.todo.api.errors.errorcode;

import jungle.study.todo.api.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TodoErrorCode implements ErrorCode {
    INVALID_STATUS_TODO(HttpStatus.FORBIDDEN, "status is invalid")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
