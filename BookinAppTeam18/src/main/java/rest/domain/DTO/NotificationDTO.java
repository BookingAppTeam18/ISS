package rest.domain.DTO;

import rest.domain.Notification;

public class NotificationDTO {

    private Long id;
    private String message;
    private long accountId;
    private Boolean Seen;


    public NotificationDTO(Long id, String message, long accountId, Boolean seen) {
        this.id = id;
        this.message = message;
        this.accountId = accountId;
        this.Seen = seen;
        if(seen == null)
            this.Seen = false;
    }

    public NotificationDTO(Notification notification ) {
        this.id = notification.getId();
        this.message = notification.getMessage();
        this.accountId = notification.getAccountId();
        Seen = notification.getSeen();
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
        return Seen;
    }

    public void setSeen(Boolean seen) {
        Seen = seen;
    }
}
