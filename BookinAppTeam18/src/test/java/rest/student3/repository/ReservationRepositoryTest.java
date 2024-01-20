package rest.student3.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import rest.domain.Reservation;
import rest.domain.enumerations.ReservationStatus;
import rest.repository.ReservationRepository;

import java.util.Calendar;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;


@DataJpaTest
@ActiveProfiles("test")
public class ReservationRepositoryTest {

    @Autowired
    private ReservationRepository reservationRepository;

    @Test
    public void shouldSaveReservation(){
        Date mockStartDate = new Date(2025, 1, 1);
        Date mockEndDate = new Date(mockStartDate.getTime());

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        Reservation reservation = new Reservation(1L, mockStartDate, mockEndDate, 4000, 1L, 3L, 2, ReservationStatus.CREATED);
        Reservation savedReservation = reservationRepository.save(reservation);

        assertThat(savedReservation).usingRecursiveComparison().isEqualTo(reservation);

    }
}
