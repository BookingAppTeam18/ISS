package rest.service;

//import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.Accommodation;
import rest.domain.AccommodationRequest;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccommodationRequestDTO;
import rest.domain.enumerations.AccommodationState;
import rest.repository.AccommodationRequestRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class AccommodationRequestService implements IService<AccommodationRequestDTO> {

    @Autowired
    private AccommodationRequestRepository accommodationRequestRepository;

    @Override
    public Collection<AccommodationRequestDTO> findAll() {
        ArrayList<AccommodationRequestDTO> accommodationDTOS = new ArrayList<AccommodationRequestDTO>();
        for (AccommodationRequest a : accommodationRequestRepository.findAll()){
            accommodationDTOS.add(new AccommodationRequestDTO(a));
        }
        return accommodationDTOS;
    }

    @Override
    public AccommodationRequestDTO findOne(Long id) {
        Optional<AccommodationRequest> accommodation = accommodationRequestRepository.findById(id);
        if (accommodation.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new AccommodationRequestDTO(accommodation.get());
    }

    @Override
    public AccommodationRequestDTO insert(AccommodationRequestDTO accommodationRequestDTO) throws Exception {
        AccommodationRequest accommodation = new AccommodationRequest(accommodationRequestDTO);
        try {
            accommodationRequestRepository.save(accommodation);
            accommodationRequestRepository.flush();
            return accommodationRequestDTO;
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
    public AccommodationRequestDTO update(AccommodationRequestDTO accommodationRequestDTO) throws Exception {
        AccommodationRequest accommodationToUpdate = new AccommodationRequest(accommodationRequestDTO);
        try {
            findOne(accommodationRequestDTO.getId()); // this will throw ResponseStatusException if student is not found
            accommodationRequestRepository.save(accommodationToUpdate);
            accommodationRequestRepository.flush();
            return accommodationRequestDTO;
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
    public AccommodationRequestDTO delete(Long id) {
        AccommodationRequest accommodation = new AccommodationRequest(findOne(id)); // this will throw StudentNotFoundException if student is not found
        accommodationRequestRepository.delete(accommodation);
        accommodationRequestRepository.flush();
        return new AccommodationRequestDTO(accommodation);
    }

    @Override
    public void deleteAll() {
        accommodationRequestRepository.deleteAll();
        accommodationRequestRepository.flush();
    }

    public Collection<AccommodationRequestDTO> filterAccommodationRequestByState(AccommodationState state){
        ArrayList<AccommodationRequestDTO>  accommodationType= new ArrayList<>();
        for(AccommodationRequest a :accommodationRequestRepository.findAccommodationRequestType(state)){
            accommodationType.add(new AccommodationRequestDTO(a));
        }
        return accommodationType;
    }

}
