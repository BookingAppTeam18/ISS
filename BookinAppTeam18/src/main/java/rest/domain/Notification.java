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
    private long accountId;
    private Boolean seen;


    public Notification(Long id, String message, long accountId, Boolean seen) {
        this.id = id;
        this.message = message;
        this.accountId = accountId;
        this.seen = seen;
    }
    public Notification(NotificationDTO notificationDTO) {
        this.id = notificationDTO.getId();
        this.message = notificationDTO.getMessage();
        this.accountId = notificationDTO.getAccountId();
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

    public long getAccountId() {
        return accountId;
    }

    public void setAccountId(long accountId) {
        this.accountId = accountId;
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
