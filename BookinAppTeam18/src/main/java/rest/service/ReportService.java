package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.AccommodationComment;
import rest.domain.Comment;
import rest.domain.DTO.CommentDTO;
import rest.domain.Report;
import rest.domain.DTO.ReportDTO;
import rest.repository.AccommodationCommentRepository;
import rest.repository.AccountCommentRepository;
import rest.repository.AccountRepository;
import rest.repository.ReportRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class ReportService implements IService<ReportDTO>{

    @Autowired
    private ReportRepository reportRepository;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private CommentService commentService;
    @Override
    public Collection<ReportDTO> findAll() {

        ArrayList<ReportDTO> reportsDTO = new ArrayList<>();
        for(Report report:reportRepository.findAll()){
            reportsDTO.add(new ReportDTO(report));
        }
        return reportsDTO;
    }

    @Override
    public ReportDTO findOne(Long id)
    {
        Optional<Report> found = reportRepository.findById(id);
        if (found.isEmpty()) {
            String value = "Not Found";
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, value);
        }
        return new ReportDTO(found.get());
    }

    @Override
    public ReportDTO insert(ReportDTO reportDTO){
        Report report = new Report(reportDTO);
        report.setReportedBy(accountRepository.getOne(reportDTO.getReportedById()));
        if(reportDTO.getReportedCommentId() != -1){
            CommentDTO found = commentService.findOne(reportDTO.getReportedCommentId());
            if(found == null)
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Comment Doesn't exist");
        }
        if(reportDTO.getReportedUserId() == -1){
            CommentDTO commentReported = commentService.findOne(reportDTO.getReportedCommentId());
            report.setReportedUser(accountRepository.getOne(commentReported.getWrittenById()));
        }
        else{
            report.setReportedUser(accountRepository.getOne(reportDTO.getReportedUserId()));
        }
       try {
            Report savedReport = reportRepository.save(report);
            reportRepository.flush();
            return new ReportDTO(savedReport);
        } catch (ConstraintViolationException ex) {
            Set<ConstraintViolation<?>> errors = ex.getConstraintViolations();
            StringBuilder sb = new StringBuilder(1000);
            for (ConstraintViolation<?> error : errors) {
                sb.append(error.getMessage() + "\n");
            }
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
        }
    }

    @Override
    public ReportDTO update(ReportDTO reportDTO) throws Exception {
        Report reportToUpdate = new Report(reportDTO);
        reportToUpdate.setReportedBy(accountRepository.getOne(reportDTO.getReportedById()));
        reportToUpdate.setReportedUser(accountRepository.getOne(reportDTO.getReportedUserId()));
        CommentDTO found = commentService.findOne(reportDTO.getReportedCommentId());
        if(found == null)
            throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, "Comment Doesn't exist");
        try {
            findOne(reportDTO.getId()); // this will throw ResponseStatusException if student is not found
            Report updatedReport = reportRepository.save(reportToUpdate);
            reportRepository.flush();
            return new ReportDTO(updatedReport);
        } catch (RuntimeException ex) {
            Throwable e = ex;
            Throwable c = null;
            while ((e != null) && !((c = e.getCause()) instanceof ConstraintViolationException) ) {
                e = (RuntimeException) c;
            }
            if ((c != null) && (c instanceof ConstraintViolationException)) {
                ConstraintViolationException c2 = (ConstraintViolationException) c;
                Set<ConstraintViolation<?>> errors = c2.getConstraintViolations();
                StringBuilder sb = new StringBuilder(1000);
                for (ConstraintViolation<?> error : errors) {
                    sb.append(error.getMessage() + "\n");
                }
                throw new ResponseStatusException(HttpStatus.NOT_ACCEPTABLE, sb.toString());
            }
            throw ex;
        }
    }

    @Override
    public ReportDTO delete(Long id) {
        ReportDTO found = findOne(id); // this will throw StudentNotFoundException if student is not found
        reportRepository.delete(new Report(found));
        reportRepository.flush();
        return found;
    }

    @Override
    public void deleteAll() {
        reportRepository.deleteAll();
        reportRepository.flush();
    }
    public Collection<ReportDTO> findAccountReports(Long accountId) {
        ArrayList<ReportDTO>  accountReports= new ArrayList<>();
        for(Report accountReport:reportRepository.findAccountReports(accountId)){
            accountReports.add(new ReportDTO(accountReport));
        }
        return accountReports;
    }

    public Collection<ReportDTO> findCommentReports(Long commentId) {
        ArrayList<ReportDTO>  commentReports= new ArrayList<>();
        for(Report commentReport:reportRepository.findCommentReports(commentId)){
            commentReports.add(new ReportDTO(commentReport));
        }
        return commentReports;
    }

}
