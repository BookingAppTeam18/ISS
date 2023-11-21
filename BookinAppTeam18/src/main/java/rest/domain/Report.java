package rest.domain;

public class Report {
    private Long id;
    private String Description;
    private long reportedUserId;
    private long reportedById;
    private long reportedComment;

    public Report(Long id, String description, long reportedUserId, long reportedById, long reportedComment) {
        this.id = id;
        Description = description;
        this.reportedUserId = reportedUserId;
        this.reportedById = reportedById;
        this.reportedComment = reportedComment;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public long getReportedUserId() {
        return reportedUserId;
    }

    public void setReportedUserId(long reportedUserId) {
        this.reportedUserId = reportedUserId;
    }

    public long getReportedById() {
        return reportedById;
    }

    public void setReportedById(long reportedById) {
        this.reportedById = reportedById;
    }

    public long getReportedComment() {
        return reportedComment;
    }

    public void setReportedComment(long reportedComment) {
        this.reportedComment = reportedComment;
    }
}
