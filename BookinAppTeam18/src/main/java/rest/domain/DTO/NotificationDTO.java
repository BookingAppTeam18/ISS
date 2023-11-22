package rest.domain.DTO;

import rest.domain.Notification;

public class NotificationDTO {

    private Long id;
    private String message;
    private long accountId;


    public NotificationDTO(Long id, String message, long accountId) {
        this.id = id;
        this.message = message;
        this.accountId = accountId;
    }

    public NotificationDTO(Notification notification) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.accountId = notification.getAccountId();
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
}
