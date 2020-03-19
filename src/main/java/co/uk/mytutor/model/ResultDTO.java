package co.uk.mytutor.model;

public class ResultDTO<T> {
    T result;

    public ResultDTO(T result) {
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
