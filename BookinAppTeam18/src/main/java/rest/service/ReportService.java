package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rest.domain.Report;
import rest.repository.CommentRepository;

import java.util.Collection;

@Service
public class ReportService implements IService<Report>{

    @Autowired
    private CommentRepository commentRepository;


    @Override
    public Collection<Report> findAll() {
        return null;
    }

    @Override
    public Report findOne(Long id) {
        return null;
    }

    @Override
    public Report create(Report object) throws Exception {
        return null;
    }

    @Override
    public Report update(Report object) throws Exception {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
