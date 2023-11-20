package rest.domain;

public class Comment {
    private Long id;
    private String message;
    private int rate;
    private long writtenById;

    //private long accountTo;

    public Comment(Long id, String message, int rate, long writtenById) {
        this.id = id;
        this.message = message;
        this.rate = rate;
        this.writtenById = writtenById;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public long getWrittenById() {
        return writtenById;
    }

    public void setWrittenById(long writtenById) {
        this.writtenById = writtenById;
    }

    public void copyValues(Comment comment) {
        this.message = comment.message;
        this.rate = comment.rate;
        this.writtenById = comment.writtenById;
    }

}
