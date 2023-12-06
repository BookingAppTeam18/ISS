package rest.domain;

import rest.domain.DTO.ReportDTO;

import javax.persistence.*;


@Entity
@Table(name="reports")
public class Report{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id",length = 5)
    private Long id;
    private String Description;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "reported_user_id")
    private Account reportedUser;
    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name = "reported_by_id")
    private Account reportedBy;
    private Long reportedCommentId;

    public Report(Long id, String description, Account reportedUser, Account reportedBy, Long reportedCommentId) {
        this.id = id;
        Description = description;
        this.reportedUser = reportedUser;
        this.reportedBy = reportedBy;
        this.reportedCommentId = reportedCommentId;
    }

    public Report(ReportDTO reportDTO) {
        this.id = reportDTO.getId();
        this.Description = reportDTO.getDescription();
        this.reportedCommentId = reportDTO.getReportedCommentId();
    }

    public Report() {

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

    public Account getReportedUser() {
        return reportedUser;
    }

    public void setReportedUser(Account reportedUser) {
        this.reportedUser = reportedUser;
    }

    public Account getReportedBy() {
        return reportedBy;
    }

    public void setReportedBy(Account reportedBy) {
        this.reportedBy = reportedBy;
    }

    public Long getReportedCommentId() {
        return reportedCommentId;
    }

    public void setReportedCommentId(Long reportedCommentId) {
        this.reportedCommentId = reportedCommentId;
    }
}
