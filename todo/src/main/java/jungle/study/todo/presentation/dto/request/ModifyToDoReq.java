package jungle.study.todo.presentation.dto.request;

import jungle.study.todo.domain.Category;

import java.util.UUID;

public record ModifyToDoReq(UUID uuid, String title, String contents, Category category) {
}
