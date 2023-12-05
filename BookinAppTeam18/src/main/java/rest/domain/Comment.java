package rest.domain;


import rest.domain.DTO.CommentDTO;
import rest.domain.enumerations.Page;

import javax.persistence.*;

@Entity
@Table(name="comments")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",length = 5)
    private Long id;
    private String message;
    private int rate;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "writtenById")
    private Account writtenBy;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "writtenToId")
    private Account writtenTo;

    @Enumerated(EnumType.STRING)
    private Page page;

    public Comment() {

    }

    public Comment(Long id, String message, int rate, Page page) {
        this.id = id;
        this.message = message;
        this.rate = rate;
        this.page = page;
    }
    public Comment(CommentDTO commentDTO) {
        this.id = commentDTO.getId();
        this.message = commentDTO.getMessage();
        this.rate = commentDTO.getRate();
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

    public Account getWrittenBy() {
        return writtenBy;
    }

    public void setWrittenBy(Account writtenBy) {
        this.writtenBy = writtenBy;
    }

    public void copyValues(Comment comment) {
        this.message = comment.message;
        this.rate = comment.rate;
        this.writtenBy = comment.writtenBy;
    }

    public Page getPage() {
        return page;
    }

    public void setPage(Page page) {
        this.page = page;
    }

    public Account getWrittenTo() {
        return writtenTo;
    }

    public void setWrittenTo(Account writtenTo) {
        this.writtenTo = writtenTo;
    }
}
