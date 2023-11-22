package rest.domain;

import rest.domain.DTO.ReportDTO;

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

    public Report(ReportDTO reportDTO) {
        this.id = reportDTO.getId();
        Description = reportDTO.getDescription();
        this.reportedUserId = reportDTO.getReportedUserId();
        this.reportedById = reportDTO.getReportedById();
        this.reportedComment = reportDTO.getReportedComment();
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
