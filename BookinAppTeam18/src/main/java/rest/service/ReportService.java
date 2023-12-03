package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.DTO.NotificationDTO;
import rest.domain.Notification;
import rest.domain.Report;
import rest.domain.DTO.ReportDTO;
import rest.domain.DTO.ReportDTO;
import rest.domain.Report;
import rest.domain.enumerations.Page;
import rest.repository.CommentRepository;
import rest.repository.ReportRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class ReportService implements IService<ReportDTO>{

    @Autowired
    private ReportRepository reportRepository;
//    ResourceBundle bundle = ResourceBundle.getBundle("ValidationMessages", LocaleContextHolder.getLocale());
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
//        if (found.isEmpty()) {
//            String value = bundle.getString("notFound");
//            throw new ResponseStatusException(HttpStatus.NOT_FOUND, value);
//        }
        return new ReportDTO(found.get());
    }

    @Override
    public ReportDTO insert(ReportDTO reportDTO){
        Report report = new Report(reportDTO);
        try {
            reportRepository.save(report);
            reportRepository.flush();
            return reportDTO;
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
        try {
            findOne(reportDTO.getId()); // this will throw ResponseStatusException if student is not found
            reportRepository.save(reportToUpdate);
            reportRepository.flush();
            return reportDTO;
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
        Report found = new Report(findOne(id)); // this will throw StudentNotFoundException if student is not found
        reportRepository.delete(found);
        reportRepository.flush();
        return new ReportDTO(found);
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

    public Collection<ReportDTO> findCommentReports(Long accountId) {
        ArrayList<ReportDTO>  commentReports= new ArrayList<>();
        for(Report commentReport:reportRepository.findCommentReports(accountId)){
            commentReports.add(new ReportDTO(commentReport));
        }
        return commentReports;
    }

}
