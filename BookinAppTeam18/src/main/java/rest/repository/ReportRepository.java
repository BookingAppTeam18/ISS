package rest.repository;

import org.springframework.stereotype.Repository;
import rest.domain.Report;

import java.util.Collection;

@Repository
public class ReportRepository implements IRepository<Report>{
    @Override
    public Collection<Report> findAll() {
        return null;
    }

    @Override
    public Report create(Report object) {
        return null;
    }

    @Override
    public Report findOne(Long id) {
        return null;
    }

    @Override
    public Report update(Report object) {
        return null;
    }

    @Override
    public void delete(Long id) {

    }
}
