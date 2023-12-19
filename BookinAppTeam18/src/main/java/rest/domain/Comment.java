package rest.domain;


import org.hibernate.annotations.Cascade;
import rest.domain.DTO.CommentDTO;
import rest.domain.enumerations.Page;

import javax.persistence.*;

@Inheritance(strategy = InheritanceType.JOINED)
@MappedSuperclass
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "comment_sequence")
    @SequenceGenerator(name = "comment_sequence", sequenceName = "comment_sequence", allocationSize = 1)
    private Long id;
    private String message;
    private int rate;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "writtenById", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private Account writtenBy;

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

}
