package jungle.study.todo.api.errors.errorcode;

import jungle.study.todo.api.errors.errorcode.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum TodoErrorCode implements ErrorCode {
    TITLE_IS_EMPTY(HttpStatus.BAD_REQUEST, "title is empty"),
    CONTENTS_IS_EMPTY(HttpStatus.BAD_REQUEST, "contents is empty"),
    INVALID_STATUS_TODO(HttpStatus.FORBIDDEN, "status is invalid"),
    TODO_NOT_FOUND(HttpStatus.NOT_FOUND, "todo is not found")
    ;

    private final HttpStatus httpStatus;
    private final String message;
}
