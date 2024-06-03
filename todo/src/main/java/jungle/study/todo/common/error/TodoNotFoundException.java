package jungle.study.todo.common.error;

public class TodoNotFoundException extends RuntimeException {

    public TodoNotFoundException(Long id) {
        super("Todo not found with id: " + id);
    }
}
