package jungle.study.todo.dto;

public class Envelope<T> {
    private T data;

    public Envelope(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}