package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.Accommodation;
import rest.domain.AccommodationComment;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccommodationDetailsDTO;
import rest.domain.DTO.CommentDTO;
<<<<<<< Updated upstream
import rest.domain.Price;
=======
import rest.domain.DTO.PriceDTO;
import rest.domain.Price;
import rest.domain.enumerations.AccommodationType;
>>>>>>> Stashed changes
import rest.repository.AccommodationCommentRepository;
import rest.repository.AccommodationRepository;
import rest.repository.AccountRepository;
import rest.repository.PriceRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class AccommodationService implements IService<AccommodationDTO> {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private AccommodationCommentRepository accommodationCommentRepository;
    @Autowired
    private PriceRepository priceRepository;

    @Autowired
    private AccountRepository accountRepository;


    @Override
    public Collection<AccommodationDTO> findAll() {
        ArrayList<AccommodationDTO> accommodationDTOS = new ArrayList<AccommodationDTO>();
        for (Accommodation a : accommodationRepository.findAll()){
            AccommodationDTO newAccommodation = new AccommodationDTO(a);
            accommodationDTOS.add(newAccommodation);

            newAccommodation.setRating(calculateRating(newAccommodation.getId()));
            newAccommodation.setNextPrice(getNextPrice(newAccommodation.getId()));
        }
        return accommodationDTOS;
    }
    public Collection<AccommodationDTO> findNAccommodations(int start, int offset) {
        ArrayList<AccommodationDTO> accommodationDTOS = new ArrayList<>();
        for (Accommodation a : accommodationRepository.findAll()){
            AccommodationDTO newAccommodation = new AccommodationDTO(a);
            accommodationDTOS.add(newAccommodation);

            newAccommodation.setRating(calculateRating(newAccommodation.getId()));
            newAccommodation.setNextPrice(getNextPrice(newAccommodation.getId()));
        }
        if(start+offset > accommodationDTOS.size())
            return accommodationDTOS.subList(start, accommodationDTOS.size());
        return accommodationDTOS.subList(start,start+offset);
    }

    private double getNextPrice(Long id) {
        Price nextAccommodationPrice =priceRepository.findNextPriceForAccommodation(id);
        if(nextAccommodationPrice != null)
            return nextAccommodationPrice.getAmount();
        return 0;
    }

    private float calculateRating(Long id) {
        Collection<AccommodationComment> accommodationComments = accommodationCommentRepository.FindAccommodationComments(id);
        float rating = 0;
        for (AccommodationComment comment:accommodationComments) {
            rating += comment.getRate();
        }
        rating /= accommodationComments.size();
        return rating;
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
        accommodation.setOwner(accountRepository.getOne(accommodationDTO.getOwnerId()));
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

<<<<<<< Updated upstream
=======
    public Collection<AccommodationDTO> findAccommodationsForOwner(Long ownerId) {
        ArrayList<AccommodationDTO>  accommodations = new ArrayList<>();
        for(Accommodation accommodation : accommodationRepository.findAccommodationsOwned(ownerId)){
            accommodations.add(new AccommodationDTO(accommodation));
        }
        return accommodations;
    }
>>>>>>> Stashed changes
}
