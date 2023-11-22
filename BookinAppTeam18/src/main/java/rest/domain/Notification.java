package rest.domain;

import rest.domain.DTO.NotificationDTO;

public class Notification {

    private Long id;
    private String message;
    private long accountId;


    public Notification(Long id, String message, long accountId) {
        this.id = id;
        this.message = message;
        this.accountId = accountId;
    }
    public Notification(NotificationDTO notificationDTO) {
        this.id = notificationDTO.getId();
        this.message = notificationDTO.getMessage();
        this.accountId = notificationDTO.getAccountId();
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
