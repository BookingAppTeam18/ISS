package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.AccommodationReport;
import rest.domain.DTO.AccommodationReportDTO;
import rest.repository.AccommodationReportRepository;
import rest.repository.AccommodationRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class AccommodationReportService implements IService<AccommodationReportDTO>{

    @Autowired
    private AccommodationReportRepository accommodationReportRepository;

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Override
    public Collection<AccommodationReportDTO> findAll() {
        ArrayList<AccommodationReportDTO> accommodationDTOS = new ArrayList<AccommodationReportDTO>();
        for (AccommodationReport a : accommodationReportRepository.findAll()){
            AccommodationReportDTO newAccommodation = new AccommodationReportDTO(a);
            accommodationDTOS.add(newAccommodation);
        }
        return accommodationDTOS;
    }

    @Override
    public AccommodationReportDTO findOne(Long id) {
        Optional<AccommodationReport> accommodation = accommodationReportRepository.findById(id);
        if (accommodation.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new AccommodationReportDTO(accommodation.get());
    }

    @Override
    public AccommodationReportDTO insert(AccommodationReportDTO accommodationReportDTO) throws Exception {
        AccommodationReport accommodation = new AccommodationReport(accommodationReportDTO);
        accommodation.setId(null);
        accommodation.setAccommodation(accommodationRepository.getOne(accommodationReportDTO.getAccommodationId()));
        try {
            AccommodationReport savedAccommodation = accommodationReportRepository.save(accommodation);
            accommodationReportRepository.flush();
            return new AccommodationReportDTO(savedAccommodation);
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
    public AccommodationReportDTO update(AccommodationReportDTO accommodationReportDTO) throws Exception {
        AccommodationReport accommodationToUpdate = new AccommodationReport(accommodationReportDTO);
        accommodationToUpdate.setAccommodation(accommodationRepository.getOne(accommodationReportDTO.getAccommodationId()));
        try {
            findOne(accommodationReportDTO.getId()); // this will throw ResponseStatusException if student is not found
            accommodationReportRepository.save(accommodationToUpdate);
            accommodationReportRepository.flush();
            return accommodationReportDTO;
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
    public AccommodationReportDTO delete(Long id) {
        AccommodationReportDTO found = findOne(id);
        AccommodationReport accommodation = new AccommodationReport(found); // this will throw StudentNotFoundException if student is not found
        accommodation.setAccommodation(accommodationRepository.getOne(found.getAccommodationId()));
        accommodationReportRepository.delete(accommodation);
        accommodationReportRepository.flush();
        return new AccommodationReportDTO(accommodation);
    }

    @Override
    public void deleteAll() {
        accommodationReportRepository.deleteAll();
        accommodationReportRepository.flush();
    }
}
