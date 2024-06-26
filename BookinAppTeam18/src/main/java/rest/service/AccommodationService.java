package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.Accommodation;
import rest.domain.AccommodationComment;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccommodationDetailsDTO;
import rest.domain.DTO.CommentDTO;
import rest.domain.Price;
import rest.domain.enumerations.AccommodationState;
import rest.repository.AccommodationCommentRepository;
import rest.repository.AccommodationRepository;
import rest.repository.AccountRepository;
import rest.repository.PriceRepository;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.*;

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

            try {
                newAccommodation.setRating(calculateRating(newAccommodation.getId()));
                newAccommodation.setNextPrice(getNextPrice(newAccommodation.getId()));
            } catch (Exception e) {
                newAccommodation.setRating(-1);
                newAccommodation.setNextPrice(-1);
            }
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
        List<Price> nextAccommodationPrice =priceRepository.findNextPriceForAccommodation(id, Pageable.ofSize(1));
        if(nextAccommodationPrice != null)
            return nextAccommodationPrice.get(0).getAmount();
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
        accommodation.setAccommodationState(AccommodationState.PENDING);
        try {
            Accommodation savedAccommodation = accommodationRepository.save(accommodation);
            accommodationRepository.flush();
            return new AccommodationDTO(savedAccommodation);
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
        accommodationToUpdate.setOwner(accountRepository.getOne(accommodationDTO.getOwnerId()));
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
    @Transactional
    public AccommodationDTO delete(Long id) {
        AccommodationDTO found = findOne(id);
        Accommodation accommodation = new Accommodation(found); // this will throw StudentNotFoundException if student is not found
        accommodation.setOwner(accountRepository.getOne(found.getOwnerId()));
        accommodationRepository.deleteBenefitsByAccommodationId(id);
        priceRepository.deletePricesByAccommodationId(id);
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

    public Collection<AccommodationDTO> findAccommodationsForOwner(Long ownerId) {
        ArrayList<AccommodationDTO>  accommodations = new ArrayList<>();
        for(Accommodation accommodation : accommodationRepository.findAccommodationsOwned(ownerId)){
            accommodations.add(new AccommodationDTO(accommodation));
        }
        return accommodations;
    }

    public Collection<AccommodationDTO> findPendingAccommodations(){
        ArrayList<AccommodationDTO> accommodations = new ArrayList<>();
        for(Accommodation accommodation : accommodationRepository.findAll()){
            if(accommodation.getAccommodationState().equals(AccommodationState.PENDING))
                accommodations.add(new AccommodationDTO(accommodation));
        }
        return accommodations;
    }

    public AccommodationDTO approveAccommodation(Long accommodationId, int option) {
        Accommodation accommodation = accommodationRepository.getOne(accommodationId);
        System.out.println("approveAccommodation method called with id: " + accommodationId + ", option: " + option);
        if(option == 0)
            accommodation.setAccommodationState(AccommodationState.DECLINED);
        else
            accommodation.setAccommodationState(AccommodationState.APPROVED);
        accommodationRepository.save(accommodation);
        accommodationRepository.flush();
        return new AccommodationDTO(accommodation);
    }
}
