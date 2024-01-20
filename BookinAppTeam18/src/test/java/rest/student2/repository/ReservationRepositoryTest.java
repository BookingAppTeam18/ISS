package rest.student2.repository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import rest.domain.Price;
import rest.domain.Reservation;
import rest.domain.enumerations.ReservationStatus;
import rest.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
public class ReservationRepositoryTest {
    @Autowired
    private ReservationRepository reservationRepository;

    @BeforeEach
    void setUp(){
        reservationRepository.deleteAll();
    }
    @Test
    public void shouldSaveReservation(){
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        Reservation reservation = new Reservation(1L, mockStartDate, mockEndDate, 400.0, 3L, 3L, 2, ReservationStatus.APPROVED);

        Reservation savedReservation = reservationRepository.save(reservation);
        
        assertThat(savedReservation).usingRecursiveComparison().isEqualTo(reservation);

    }

    @Test
    public void shouldReplaceIfExistingReservation() {
        // Arrange
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        Reservation reservation = new Reservation(1L, mockStartDate, mockEndDate, 400.0, 3L, 3L, 2, ReservationStatus.APPROVED);

        // Save the reservation once
        reservationRepository.save(reservation);

        // Try to save another reservation with the same ID
        Reservation duplicateReservation = new Reservation(1L, mockStartDate, mockEndDate, 100.0, 3L, 3L, 2, ReservationStatus.APPROVED);
        Reservation savedReservation = reservationRepository.save(duplicateReservation);

        // Assert
        assertNotNull(savedReservation);
        assertEquals(reservation.getId(), savedReservation.getId());

    }


    @Test
    public void shouldCheckIfReservationExists() {
        // Arrange
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        Reservation reservation = new Reservation(1L, mockStartDate, mockEndDate, 400.0, 3L, 3L, 2, ReservationStatus.APPROVED);

        // Save the reservation to the database
        Reservation savedReservation = reservationRepository.save(reservation);

        // Act
        boolean reservationExists = reservationRepository.existsById(savedReservation.getId());

        // Assert
        assertThat(reservationExists).isTrue();
    }

    @Test
    public void shouldCheckIfReservationDoesNotExist() {
        // Arrange
        Long nonExistingReservationId = 999L; // Assuming this ID does not exist in the database

        // Act
        boolean reservationExists = reservationRepository.existsById(nonExistingReservationId);

        // Assert
        assertThat(reservationExists).isFalse();
    }

    @Test
    public void testFindByAccommodationId() {
        // Arrange
        Long accommodationId = 1L; // zamijenite s vašim stvarnim accommodationId
        Reservation reservation = new Reservation(1L, new Date(), new Date(), 100.0, 1L, accommodationId, 1, ReservationStatus.APPROVED);
        Reservation noAccommodationIdReservation = new Reservation(2L, new Date(), new Date(), 100.0, 1L, 2L, 1, ReservationStatus.APPROVED);
        reservationRepository.save(reservation);
        reservationRepository.save(noAccommodationIdReservation);

        // Act
        Collection<Reservation> reservations = reservationRepository.findByAccommodationId(accommodationId);

        // Assert
        assertFalse(reservations.isEmpty());
        assertThat(reservations.size()).isEqualTo(1);
//        assertThat(reservations).contains(reservation);
        assertTrue(reservations.contains(reservation));
    }

    @Test
    public void testFindByAccommodationIdNoReservations() {
        // Arrange
        Long accommodationId = 1L; // zamijenite s vašim stvarnim accommodationId
        Reservation noAccommodationIdReservation = new Reservation(2L, new Date(), new Date(), 100.0, 1L, 2L, 1, ReservationStatus.APPROVED);
        reservationRepository.save(noAccommodationIdReservation);

        // Act
        Collection<Reservation> reservations = reservationRepository.findByAccommodationId(accommodationId);

        // Assert
        assertTrue(reservations.isEmpty());
    }
}
