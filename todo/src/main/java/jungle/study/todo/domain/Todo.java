package jungle.study.todo.domain;

public class Todo {
    private Long id;
    private String title;
    private String contents;
    private Status status;

    public Todo(String title, String contents, Status status) {
        this.title = title;
        this.contents = contents;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
