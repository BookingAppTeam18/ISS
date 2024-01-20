package rest.student2.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import rest.domain.DTO.ReservationDTO;
import rest.domain.Reservation;
import rest.domain.enumerations.ReservationStatus;
import rest.repository.ReservationRepository;
import rest.service.ReservationService;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceApproveTest {

    @InjectMocks
    private ReservationService reservationService;
    @Mock
    private ReservationRepository reservationRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testApproveReservationNotFound() {
        // Arrange
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setId(999L);

        when(reservationRepository.existsById(anyLong())).thenReturn(false);
        when(reservationRepository.getOne(anyLong())).thenReturn(new Reservation(reservationDTO));

        ResponseStatusException responseStatusException = assertThrows(ResponseStatusException.class, () -> reservationService.approveReservation(reservationDTO));
        assertEquals(HttpStatus.NOT_FOUND, responseStatusException.getStatus());
        assertEquals("404 NOT_FOUND", responseStatusException.getMessage());

//        assertThrows(ResponseStatusException.class, () -> reservationService.approveReservation(reservationDTO));
        verify(reservationRepository, never()).save(any());
    }

    @Test
    void testApproveReservationONoOverlap() {
        // Arrange
        Reservation firstReservation = new Reservation(1L, new Date(2024-1900, 1,28), new Date(2024-1900, 1, 29), 100.0, 1L,1L,1, ReservationStatus.CREATED);
        Reservation secondReservation = new Reservation(2L, new Date(2024-1900, 1,30), new Date(2024-1900, 1, 31), 100.0, 1L,1L,1, ReservationStatus.CREATED);

        ReservationDTO firstReservationDTO = new ReservationDTO(firstReservation);
        ReservationDTO secondReservationDTO = new ReservationDTO(secondReservation);

        when(reservationRepository.existsById(anyLong())).thenReturn(true);
        when(reservationRepository.getOne(1L)).thenReturn(firstReservation);
        when(reservationRepository.getOne(2L)).thenReturn(secondReservation);
        when(reservationRepository.findByAccommodationId(anyLong())).thenReturn(Arrays.asList(firstReservation, secondReservation));

        // Act
        ReservationDTO result = reservationService.approveReservation(firstReservationDTO);

        // Assert
        assertEquals(ReservationStatus.APPROVED, firstReservation.getReservationStatus());
        assertEquals(ReservationStatus.CREATED, secondReservation.getReservationStatus());

        // Check that other reservations remain in their original statuses
//        verify(reservationRepository, never()).save(argThat(res -> res.getReservationStatus() != ReservationStatus.CREATED && res.getReservationStatus() != ReservationStatus.APPROVED));
        verify(reservationRepository, times(1)).findByAccommodationId(anyLong());
        verify(reservationRepository, times(1)).save(firstReservation);
        verify(reservationRepository, never()).save(secondReservation);
        verify(reservationRepository, times(1)).flush();
        // Assert
        assertEquals(firstReservationDTO, result);

    }


    @Test
    void testApproveReservationOverlap() {
        // Arrange
        Reservation firstReservation = new Reservation(1L, new Date(2024 - 1900, 1, 21), new Date(2024 - 1900, 1, 29), 100.0, 1L, 1L, 1, ReservationStatus.CREATED);
        Reservation secondReservation = new Reservation(2L, new Date(2024 - 1900, 1, 22), new Date(2024 - 1900, 1, 23), 100.0, 1L, 1L, 1, ReservationStatus.CREATED);

        ReservationDTO firstReservationDTO = new ReservationDTO(firstReservation);

        when(reservationRepository.existsById(anyLong())).thenReturn(true);
        when(reservationRepository.getOne(1L)).thenReturn(firstReservation);
        when(reservationRepository.getOne(2L)).thenReturn(secondReservation);
        when(reservationRepository.findByAccommodationId(anyLong())).thenReturn(Arrays.asList(firstReservation, secondReservation));
        ArgumentCaptor<Reservation> reservationCaptor = ArgumentCaptor.forClass(Reservation.class);

        // Act
        ReservationDTO result = reservationService.approveReservation(firstReservationDTO);

        // Assert

        verify(reservationRepository, times(1)).findByAccommodationId(anyLong());
        verify(reservationRepository, times(2)).save(reservationCaptor.capture());
        verify(reservationRepository, times(1)).flush();

        // Get the saved reservations
        List<Reservation> savedReservations = reservationCaptor.getAllValues();
        assertEquals(ReservationStatus.APPROVED, result.getReservationStatus());
        assertEquals(ReservationStatus.APPROVED, savedReservations.get(0).getReservationStatus());
        assertEquals(ReservationStatus.DENIED, savedReservations.get(1).getReservationStatus());
        assertEquals(result, firstReservationDTO);


    }



}
