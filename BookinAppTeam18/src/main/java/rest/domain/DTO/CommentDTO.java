package rest.domain.DTO;

import rest.domain.AccommodationComment;
import rest.domain.AccountComment;
import rest.domain.Comment;
import rest.domain.enumerations.Page;

public class CommentDTO {
    private Long id;
    private String message;
    private int rate;
    private long writtenById;
    private String writtenByName;
    private long writtenToId;
    private Page page;
    public CommentDTO( ) {
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

    public long getWrittenToId() {
        return writtenToId;
    }

    public void setWrittenToId(long writtenToId) {
        this.writtenToId = writtenToId;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public CommentDTO(AccommodationComment comment) {
        this.id = comment.getId();
        this.message = comment.getMessage();
        this.rate = comment.getRate();
        this.writtenToId = comment.getAccommodation().getId();
        this.writtenById = comment.getWrittenBy().getId();
        this.writtenByName = comment.getWrittenBy().getFirstName() +" " + comment.getWrittenBy().getLastName();
        this.page = comment.getPage();
    }

    public CommentDTO(AccountComment comment) {
        this.id = comment.getId();
        this.message = comment.getMessage();
        this.rate = comment.getRate();
        this.writtenById = comment.getAccount().getId();
        this.writtenById = comment.getWrittenBy().getId();
        this.writtenByName = comment.getWrittenBy().getFirstName() +" " + comment.getWrittenBy().getLastName();
        this.page = comment.getPage();
    }

    public String getWrittenByName() {
        return writtenByName;
    }

    public void setWrittenByName(String writtenByName) {
        this.writtenByName = writtenByName;
    }
}
