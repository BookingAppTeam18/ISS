package rest.domain;

import org.hibernate.annotations.Cascade;
import rest.domain.DTO.CommentDTO;
import rest.domain.enumerations.Page;

import javax.persistence.*;

@Entity
@Table(name="AccountComments")
public class AccountComment extends Comment{

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name = "accountId", nullable = false)
    @Cascade(org.hibernate.annotations.CascadeType.REMOVE)
    private Account account;
    public void setAccountId(Account accountId) {
        this.account = accountId;
    }

    public AccountComment() {
    }


    public AccountComment(CommentDTO commentDTO) {
        super(commentDTO);
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
