package jungle.study.todo.domain;

public enum Category {
    TODO("ToDo"),
    DOING("Doing"),
    DONE("Done");

    private final String status;

    Category(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return this.status;
    }
}
