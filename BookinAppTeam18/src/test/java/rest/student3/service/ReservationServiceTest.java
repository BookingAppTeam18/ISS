package rest.student3.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.Accommodation;
import rest.domain.DTO.ReservationDTO;
import rest.domain.Reservation;
import rest.domain.enumerations.ReservationStatus;
import rest.repository.AccommodationRepository;
import rest.repository.ReservationRepository;
import rest.service.ReservationService;

import javax.validation.ConstraintViolationException;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(MockitoExtension.class)
public class ReservationServiceTest {

    @InjectMocks
    private ReservationService reservationService;

    @MockBean
    private ReservationRepository reservationRepository;

    @Mock
    private AccommodationRepository accommodationRepository;

    @Test
    public void testInsertCreatedNonAutomaticallyReservedAccommodation() throws Exception {
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        ReservationDTO reservationDTO = new ReservationDTO(1L, mockStartDate, mockEndDate, 4000, 1L, 3L, 2, ReservationStatus.CREATED);
        reservationDTO.setAccommodationId(1L);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setAutomaticallyReserved(false);

        Mockito.when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));
        Mockito.when(reservationRepository.save(any(Reservation.class)))
                .thenAnswer(invocation -> {
                    // Ovde možete simulirati ponašanje save metode
                    Reservation savedReservation = invocation.getArgument(0);
                    savedReservation.setId(1L); // Postavite ID kako bi simulirali da je rezervacija uspešno sačuvana u bazi
                    return savedReservation;
                });

        // Pozivanje metode koju želite testirati
        ReservationDTO result = reservationService.insert(reservationDTO);

        // Provera da li je rezultat ono što očekujete
        assertNotNull(result);
        assertEquals(ReservationStatus.CREATED, result.getReservationStatus());

        // Verifikacija poziva metoda repozitorijuma
        verify(accommodationRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).save(any());
    }

    @Test
    public void testInsertApprovedAutomaticallyReservedAccommodation() throws Exception {
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        ReservationDTO reservationDTO = new ReservationDTO(1L, mockStartDate, mockEndDate, 4000, 1L, 3L, 2, ReservationStatus.CREATED);
        reservationDTO.setAccommodationId(1L);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setAutomaticallyReserved(true);

        Mockito.when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));
        Mockito.when(reservationRepository.save(any(Reservation.class))).thenAnswer(invocation -> {
            Reservation savedReservation = invocation.getArgument(0);
            savedReservation.setId(1L);
            savedReservation.setReservationStatus(ReservationStatus.APPROVED);
            reservationDTO.setReservationStatus(ReservationStatus.APPROVED);
            return savedReservation;
        });

        ReservationDTO result = reservationService.insert(reservationDTO);

        assertNotNull(result);
        assertEquals(ReservationStatus.APPROVED, result.getReservationStatus());

        verify(accommodationRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).save(any());
    }


    @Test
    public void testInsertExceptionCase() {
        Date mockStartDate = new Date(2023, 5, 19);
        Date mockEndDate = new Date(2023, 5, 20);

        ReservationDTO reservationDTO = new ReservationDTO(1L, mockStartDate, mockEndDate, 4000, 1L, 1L, 2, ReservationStatus.CREATED);
        reservationDTO.setStartDate(mockStartDate);
        reservationDTO.setEndDate(mockEndDate);

        Accommodation accommodation = new Accommodation();
        accommodation.setId(1L);
        accommodation.setAutomaticallyReserved(true);

        // Postavljanje ponašanja mock-ovanih repozitorijuma
        Mockito.when(accommodationRepository.findById(1L)).thenReturn(Optional.of(accommodation));

        // Simuliranje ConstraintViolationException prilikom čuvanja rezervacije
        Mockito.when(reservationRepository.save(any())).thenThrow(new ConstraintViolationException("Invalid data", null));

        // Pozivanje metode koju želite testirati i očekivanje izuzetka
        assertThrows(ResponseStatusException.class, () -> reservationService.insert(reservationDTO));

        // Verifikacija poziva metoda repozitorijuma
        verify(accommodationRepository, times(1)).findById(1L);
        verify(reservationRepository, times(1)).save(any());
    }

}
