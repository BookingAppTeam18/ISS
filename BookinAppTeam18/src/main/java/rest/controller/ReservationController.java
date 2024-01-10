package rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import rest.domain.DTO.AccommodationDTO;
import rest.domain.DTO.AccountDTO;
import rest.domain.DTO.ReservationDTO;
import rest.service.ReservationService;

import java.util.Collection;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    //Get all reservations
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReservationDTO>> getReservations() {
        Collection<ReservationDTO> reservations = reservationService.findAll();
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    //Get reservation by id
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    @GetMapping(value="/reservation/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> getReservationById(@PathVariable("id") Long reservationId) {
        ReservationDTO reservationDTO = reservationService.findOne(reservationId);
        if(reservationDTO == null){
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reservationDTO, HttpStatus.OK);
    }

    //Get all users who had reservation in specific accommodation
    @PreAuthorize("hasAnyAuthority('ADMIN', 'OWNER')")
    @GetMapping(value="/accommodation/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccountDTO>> getAccommodationGuests(@PathVariable("id") Long accommodationId) {
        Collection<AccountDTO> accounts = reservationService.findAccommodationGuests(accommodationId);
        return new ResponseEntity<>(accounts, HttpStatus.OK);
    }

    //Get all
    @PreAuthorize("hasAnyAuthority('ADMIN', 'GUEST')")
    @GetMapping(value="/account/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<AccommodationDTO>> getGuestAccommodations(@PathVariable("id") Long accountId) {
        Collection<AccommodationDTO> accommodations = reservationService.findUserAccommodations(accountId);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('ADMIN', 'GUEST','OWNER')")
    @GetMapping(value="/{id}",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Collection<ReservationDTO>> getGuestReservations(@PathVariable("id") Long accountId) {
        Collection<ReservationDTO> reservations = reservationService.findUserReservations(accountId);
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    // Create reservation
    @PreAuthorize("hasAnyAuthority('GUEST')")
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE,produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> createReservation(@RequestBody ReservationDTO reservationDTO) throws Exception{
        return new ResponseEntity<>(reservationService.insert(reservationDTO), HttpStatus.CREATED);
    }

    //Update reservation
    @PreAuthorize("hasAnyAuthority('GUEST')")
    @PutMapping(value = "/{reservationId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ReservationDTO> updateReservation(@RequestBody ReservationDTO reservationDTO, @PathVariable Long reservationId)
            throws Exception {
        reservationDTO.setId(reservationId);
        ReservationDTO updatedReservation = reservationService.update(reservationDTO);

        if (updatedReservation == null) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
    }


    //Delete reservation
    @PreAuthorize("hasAnyAuthority('GUEST')")
    @DeleteMapping(value = "/{reservationId}")
    public ResponseEntity<AccountDTO> deleteReservation(@PathVariable("reservationId") Long reservationId) {
        reservationService.delete(reservationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
