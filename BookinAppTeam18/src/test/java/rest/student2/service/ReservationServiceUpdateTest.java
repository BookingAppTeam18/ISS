package rest.student2.service;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.DTO.ReservationDTO;
import rest.domain.Reservation;
import rest.domain.enumerations.ReservationStatus;
import rest.repository.ReservationRepository;
import rest.service.ReservationService;

import java.util.Date;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ReservationServiceUpdateTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    @Test
    public void testUpdateExistingReservation() throws Exception {
        Date startDate = new Date();
        Date endDate = new Date(startDate.getTime() + 7 * 24 * 60 * 60 * 1000); // Dodajemo nedelju dana
        // Priprema podataka
        Reservation existingReservation = new Reservation(1L, startDate, endDate, 100, null, null, 4,ReservationStatus.CREATED);

        ReservationDTO updatedReservationDTO = new ReservationDTO(1L, startDate, endDate, 100, null, null, 4,ReservationStatus.APPROVED);


        when(reservationRepository.findById(1L)).thenReturn(Optional.of(existingReservation));
        reservationRepository.save(existingReservation);


        // Pozivanje metode koju testiramo
        ReservationDTO resultDTO = reservationService.update(updatedReservationDTO);

        // Provjera rezultata
        verify(reservationRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).save(existingReservation);

        assertNotNull(resultDTO);
        assertEquals(ReservationStatus.APPROVED, resultDTO.getReservationStatus());
    }

    @Test
    public void testUpdateNonExistingReservation() {
        // Priprema podataka
        ReservationDTO nonExistingReservationDTO = new ReservationDTO(999L, null, null, 0.0, null, null, 0,ReservationStatus.APPROVED);

        when(reservationRepository.findById(999L)).thenReturn(Optional.empty());

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> reservationService.update(nonExistingReservationDTO));
        // Pozivanje metode koju testiramo i provjera da li baca odgovarajući izuzetak
        assertEquals("404 NOT_FOUND",responseStatusException.getMessage());
        // Provjera poziva repository metode
        verify(reservationRepository, times(1)).findById(999L);
        verify(reservationRepository, never()).save(any(Reservation.class));
    }
}
