package rest.service;

//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.Accommodation;
import rest.domain.AccommodationComment;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccommodationDetailsDTO;
import rest.domain.DTO.CommentDTO;
import rest.domain.Price;
import rest.domain.enumerations.AccommodationType;
import rest.repository.AccommodationCommentRepository;
import rest.repository.AccommodationRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

@Service
public class AccommodationService implements IService<AccommodationDTO> {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AccommodationCommentRepository accommodationCommentRepository;


    @Override
    public Collection<AccommodationDTO> findAll() {
        ArrayList<AccommodationDTO> accommodationDTOS = new ArrayList<AccommodationDTO>();
        for (Accommodation a : accommodationRepository.findAll()){
            accommodationDTOS.add(new AccommodationDTO(a));
        }
        return accommodationDTOS;
    }

    @Override
    public AccommodationDTO findOne(Long id) {
        Optional<Accommodation> accommodation = accommodationRepository.findById(id);
        if (accommodation.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new AccommodationDTO(accommodation.get());
    }

    @Override
    public AccommodationDTO insert(AccommodationDTO accommodationDTO) throws Exception {
        Accommodation accommodation = new Accommodation(accommodationDTO);
        try {
            accommodationRepository.save(accommodation);
            accommodationRepository.flush();
            return accommodationDTO;
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
    public AccommodationDTO update(AccommodationDTO accommodationDTO) throws Exception {
        Accommodation accommodationToUpdate = new Accommodation(accommodationDTO);
        try {
            findOne(accommodationDTO.getId()); // this will throw ResponseStatusException if student is not found
            accommodationRepository.save(accommodationToUpdate);
            accommodationRepository.flush();
            return accommodationDTO;
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
    public AccommodationDTO delete(Long id) {
        Accommodation accommodation = new Accommodation(findOne(id)); // this will throw StudentNotFoundException if student is not found
        accommodationRepository.delete(accommodation);
        accommodationRepository.flush();
        return new AccommodationDTO(accommodation);
    }

    @Override
    public void deleteAll() {
        accommodationRepository.deleteAll();
        accommodationRepository.flush();
    }

    public Collection<AccommodationDTO> filterAccommodationsType(AccommodationType type){
        ArrayList<AccommodationDTO>  accommodationType= new ArrayList<>();
        for(Accommodation a :accommodationRepository.findAccommodationType(type)){
            accommodationType.add(new AccommodationDTO(a));
        }
        return accommodationType;
    }

    public Collection<AccommodationDTO> filterAccommodationsLocation(double longitude, double latitude){
        ArrayList<AccommodationDTO>  accommodationType= new ArrayList<>();
        for(Accommodation a :accommodationRepository.findAccommodationLocation(longitude, latitude)){
            accommodationType.add(new AccommodationDTO(a));
        }
        return accommodationType;
    }

    public AccommodationDetailsDTO findAccommodationDetails(long accommodationId) {

        AccommodationDetailsDTO accommodationDetailsDTO = new AccommodationDetailsDTO();

        Accommodation accommodation = accommodationRepository.getOne(accommodationId);
        Collection<AccommodationComment> accommodationComments = accommodationCommentRepository.FindAccommodationComments(accommodationId);

        accommodationDetailsDTO.setAccommodationDTO(new AccommodationDTO(accommodation));
        for (AccommodationComment accommodationComment: accommodationComments) {
            accommodationDetailsDTO.getCommentsDTO().add(new CommentDTO(accommodationComment));
        }
        
        return accommodationDetailsDTO;
    }
}
