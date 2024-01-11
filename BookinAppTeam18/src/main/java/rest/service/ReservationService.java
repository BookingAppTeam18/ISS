package rest.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.Accommodation;
import rest.domain.Account;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccountDTO;
import rest.domain.DTO.ReservationDTO;
import rest.domain.Price;
import rest.domain.Reservation;
import rest.domain.enumerations.ReservationStatus;
import rest.repository.ReservationRepository;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.Set;

@Service
public class ReservationService implements IService<ReservationDTO> {

    @Autowired
    private ReservationRepository reservationRepository;
    @Override
    public Collection<ReservationDTO> findAll() {
        ArrayList<ReservationDTO> reservationDTOS = new ArrayList<ReservationDTO>();
        for (Reservation reservation : reservationRepository.findAll()){
            reservationDTOS.add(new ReservationDTO(reservation));
        }
        return reservationDTOS;
    }

    @Override
    public ReservationDTO findOne(Long id) {
        Optional<Reservation> reservation = reservationRepository.findById(id);
        if (reservation.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }
        return new ReservationDTO(reservation.get());
    }

    public Collection<AccommodationDTO> findUserAccommodations(Long userId) {
        Collection<Accommodation> accommodations = reservationRepository.findUserAccommodations(userId);
        Collection<AccommodationDTO> accommodationDTOS = null;
        for(Accommodation accommodation : accommodations){
            accommodationDTOS.add(new AccommodationDTO(accommodation));
        }
        return accommodationDTOS;
    }

    public Collection<ReservationDTO> findUserReservations(Long userId){
        Collection<Reservation> reservations = reservationRepository.findGuestReservations(userId);
        Collection<ReservationDTO> reservationDTOS = new ArrayList<ReservationDTO>();
        for(Reservation reservation : reservations){
            reservationDTOS.add(new ReservationDTO(reservation));
        }
        return reservationDTOS;
    }

    public Collection<AccountDTO> findAccommodationGuests(Long accommodationId){
        Collection<Account> accounts = reservationRepository.findAccommodationGuests(accommodationId);
        Collection<AccountDTO> accountDTOS = null;
        for(Account account : accounts){
            accountDTOS.add(new AccountDTO(account));
        }
        return accountDTOS;
    }

    public Collection<ReservationDTO> findReservationsForAccommodation(Long accommodationId){
        Collection<Reservation> reservations = reservationRepository.findByAccommodationId(accommodationId);
        Collection<ReservationDTO> reservationDTOS = new ArrayList<ReservationDTO>();
        for(Reservation reservation : reservations){
            reservationDTOS.add(new ReservationDTO(reservation));
        }
        return reservationDTOS;
    }

    @Override
    public ReservationDTO insert(ReservationDTO reservationDTO) throws Exception {
        Reservation reservation = new Reservation(reservationDTO);
        try {
            reservationRepository.save(reservation);
            reservationRepository.flush();
            return reservationDTO;
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
    public ReservationDTO update(ReservationDTO reservationDTO) throws Exception {
        Reservation reservationToUpdate = new Reservation(reservationDTO);
        try {
            findOne(reservationDTO.getId()); // this will throw ResponseStatusException if student is not found
            reservationToUpdate.setReservationStatus(reservationDTO.getReservationStatus());
            reservationRepository.save(reservationToUpdate);
            reservationRepository.flush();
            return reservationDTO;
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
    public ReservationDTO delete(Long id) {
        Reservation reservation = new Reservation(findOne(id)); // this will throw StudentNotFoundException if student is not found
        reservationRepository.delete(reservation);
        reservationRepository.flush();
        return new ReservationDTO(reservation);
    }

    @Override
    public void deleteAll() {
        reservationRepository.deleteAll();
        reservationRepository.flush();
    }
}
