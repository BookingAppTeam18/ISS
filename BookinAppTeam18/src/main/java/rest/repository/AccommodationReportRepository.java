package rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.domain.AccommodationReport;

public interface AccommodationReportRepository extends JpaRepository<AccommodationReport,Long> {
}
