package jungle.study.todo.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.FieldError;

import java.util.List;

@Builder
@RequiredArgsConstructor
public class ErrorResponseDto {

    private final String code;
    private final String message;

    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private final List<ValidationError> errors;

    @Getter
    @Builder
    @RequiredArgsConstructor
    public static class ValidationError{

        private final String field;
        private final String message;

        public static ValidationError of(final FieldError filedError) {
            return ValidationError.builder()
                    .field(filedError.getField())
                    .message(filedError.getDefaultMessage())
                    .build();
        }
    }


}
