package rest.domain.DTO;

import rest.domain.Report;

public class ReportDTO {
    private Long id;
    private String Description;
    private long reportedUserId;
    private long reportedById;
    private long reportedCommentId;

    public ReportDTO(Long id, String description, long reportedUserId, long reportedById, long reportedCommentId) {
        this.id = id;
        Description = description;
        this.reportedUserId = reportedUserId;
        this.reportedById = reportedById;
        this.reportedCommentId = reportedCommentId;
    }

    public ReportDTO(){

    }

    public ReportDTO(Report report) {
        this.id = report.getId();
        Description = report.getDescription();
        this.reportedUserId = report.getReportedUser().getId();
        this.reportedById = report.getReportedBy().getId();
        this.reportedCommentId = report.getReportedCommentId();
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

    public long getReportedCommentId() {
        return reportedCommentId;
    }

    public void setReportedCommentId(long reportedComment) {
        this.reportedCommentId = reportedComment;
    }
}
