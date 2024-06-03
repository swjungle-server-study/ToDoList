package jungle.study.todo.api.errors.exception;

import jungle.study.todo.api.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class ApiException extends RuntimeException {

    private final ErrorCode errorCode;
}
