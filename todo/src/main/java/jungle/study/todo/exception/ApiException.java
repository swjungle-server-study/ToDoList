package jungle.study.todo.exception;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException{
    private ErrorCode errorCode;

    public ApiException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}