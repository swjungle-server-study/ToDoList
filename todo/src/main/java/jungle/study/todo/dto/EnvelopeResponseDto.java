package jungle.study.todo.dto;

import jungle.study.todo.common.error.CommonErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class EnvelopeResponseDto<T> {
    private final String error;   // 에러 코드 (null이면 성공)
    private final T data;         // 응답 데이터
    private final String message; // 에러 메시지 (null이면 성공)

    // 성공 응답 생성
    public static <T> EnvelopeResponseDto<T> success(T data) {
        return new EnvelopeResponseDto<>(null, data, null);
    }

    // 에러 응답 생성
    public static <T> EnvelopeResponseDto<T> error(CommonErrorCode errorCode) {
        return new EnvelopeResponseDto<>(errorCode.name(), null, errorCode.getMessage());
    }
}
