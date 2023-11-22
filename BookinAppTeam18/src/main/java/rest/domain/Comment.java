package rest.domain;


import rest.domain.DTO.CommentDTO;
import rest.domain.enumerations.Page;

public class Comment {
    private Long id;
    private String message;
    private int rate;
    private long writtenById;
    private long writtenTo;
    private Page page;

    public Comment(Long id, String message, int rate, long writtenById,long writtenTo, Page page) {
        this.id = id;
        this.message = message;
        this.rate = rate;
        this.writtenById = writtenById;
        this.writtenTo = writtenTo;
        this.page = page;
    }
    public Comment(CommentDTO commentDTO) {
        this.id = commentDTO.getId();
        this.message = commentDTO.getMessage();
        this.rate = commentDTO.getRate();
        this.writtenById = commentDTO.getWrittenById();
        this.writtenTo = commentDTO.getWrittenTo();
        this.page = commentDTO.getPage();
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

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public long getWrittenTo() {
        return writtenTo;
    }

    public void setWrittenTo(long writtenTo) {
        this.writtenTo = writtenTo;
    }
}
