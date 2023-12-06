package rest.domain;

import rest.domain.DTO.NotificationDTO;

import javax.persistence.*;

@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",length = 5)
    private Long id;
    private String message;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "notification_for")
    private Account account;
    private Boolean seen;


    public Notification(Long id, String message, Account account, Boolean seen) {
        this.id = id;
        this.message = message;
        this.account = account;
        this.seen = seen;
    }
    public Notification(NotificationDTO notificationDTO) {
        this.id = notificationDTO.getId();
        this.message = notificationDTO.getMessage();
        this.seen = notificationDTO.getSeen();
    }

    public Notification() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "Notification{" +
                "id=" + id +
                ", message='" + message + '\'' +
                '}';
    }

    public Boolean getSeen() {
        return seen;
    }

    public void setSeen(Boolean seen) {
        this.seen = seen;
    }
}
