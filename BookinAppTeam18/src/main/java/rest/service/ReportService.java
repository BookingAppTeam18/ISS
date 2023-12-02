package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.domain.DTO.ReportDTO;
import rest.domain.Report;
import rest.repository.CommentRepository;

import java.util.Collection;

@Service
public class ReportService implements IService<ReportDTO>{

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public Collection<ReportDTO> findAll() {
        return null;
    }

    @Override
    public ReportDTO findOne(Long id) {
        return null;
    }

    @Override
    public ReportDTO insert(ReportDTO object) throws Exception {
        return null;
    }

    @Override
    public ReportDTO update(ReportDTO object) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {

    }

    public Collection<ReportDTO> findAccountReports(Long accountId) {
        return null;
    }

    public Collection<ReportDTO> findCommentReports(Long accountId) {
        return null;
    }
}
