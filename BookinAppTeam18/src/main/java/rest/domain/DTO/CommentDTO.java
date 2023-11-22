package rest.domain.DTO;

import rest.domain.Comment;
import rest.domain.enumerations.Page;

public class CommentDTO {
    private Long id;
    private String message;
    private int rate;
    private long writtenById;
    private long writtenTo;
    private Page page;

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

    public long getWrittenTo() {
        return writtenTo;
    }

    public void setWrittenTo(long writtenTo) {
        this.writtenTo = writtenTo;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public CommentDTO(Comment comment) {
        this.id = comment.getId();
        this.message = comment.getMessage();
        this.rate = comment.getRate();
        this.writtenById = comment.getWrittenById();
        this.writtenTo = comment.getWrittenTo();
        this.page = comment.getPage();
    }
}
