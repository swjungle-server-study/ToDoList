package jungle.study.todo.presentation.dto.request;

import jungle.study.todo.domain.Category;

public record CreateToDoReq(String title, String contents, Category category) {
}
