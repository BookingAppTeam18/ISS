package rest.student2.contoller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import rest.domain.DTO.PriceDTO;
import rest.domain.DTO.ReservationDTO;
import rest.domain.enumerations.ReservationStatus;
import rest.utils.TokenUtils;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
    public class ReservationControllerIntegrationTest{

        @Autowired
        private TestRestTemplate restTemplate;
        @Autowired
        private TokenUtils tokenUtils;

        @ParameterizedTest
        @MethodSource("provideTestCases")
        @DisplayName("Should change reservation status")
        public void shouldChangeReservationStatus(Long reservationId, int expectedStatusCode) {
            Date mockStartDate = new Date(2024-1900, 0,20);
            Date mockEndDate = new Date(mockStartDate.getTime());
            System.out.println(mockStartDate.toString());
            // Dodajte nedelju dana na datum završetka
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(mockEndDate);
            calendar.add(Calendar.DAY_OF_MONTH, 7);
            mockEndDate = calendar.getTime();



            String username = "owner@gmail.com";
            String jwtToken = tokenUtils.generateToken(username);

            // Postavljanje tokena u zaglavlje
            HttpHeaders headers = new HttpHeaders();
            headers.set("Authorization", "Bearer " + jwtToken);

            // Stvaranje cijele rezervacije za slanje u tijelu zahtjeva
            ReservationDTO reservationDTO = new ReservationDTO(reservationId, mockStartDate, mockEndDate, 400.0, 3L, 3L, 2, ReservationStatus.APPROVED);



            // Slanje HTTP PUT zahtjeva s cijelom rezervacijom u tijelu zahtjeva
            ResponseEntity<ReservationDTO> responseEntity = restTemplate.exchange(
                    "/api/reservations/approve/{reservationId}",
                    HttpMethod.PUT,
                    new HttpEntity<>(reservationDTO, headers),
                    ReservationDTO.class,
                    reservationId
            );

            // Provjera očekivanog status koda
            assertEquals(expectedStatusCode, responseEntity.getStatusCodeValue());

            ResponseEntity<ReservationDTO> getByIdResponse = restTemplate.exchange(
                    "/api/reservations/reservation/{id}",
                    HttpMethod.GET,
                    new HttpEntity<>(headers),
                    ReservationDTO.class,  // Ovo ostavite kako je u vašem testu
                    reservationId
            );

            if (expectedStatusCode == 404) {
                // Ako očekujete NOT_FOUND, provjerite da je status odgovora 404 i tijelo odgovora je null
                assertNull(getByIdResponse.getBody().getId());
            } else {
                // Ako očekujete drugi status, provjerite da je status odgovora onaj koji očekujete

                // Nastavite s postojećim kodom za usporedbu rezervacija
                ReservationDTO fetchedReservation = getByIdResponse.getBody();
                assertNotNull(fetchedReservation);
                assertEquals(reservationDTO.getId(), fetchedReservation.getId());
                assertEquals(reservationDTO.getStartDate(), fetchedReservation.getStartDate());
                assertEquals(reservationDTO.getEndDate(), fetchedReservation.getEndDate());
                assertEquals(reservationDTO.getPrice(), fetchedReservation.getPrice());
                assertEquals(reservationDTO.getAccommodationId(), fetchedReservation.getAccommodationId());
                assertEquals(reservationDTO.getAccountId(), fetchedReservation.getAccountId());
                assertEquals(reservationDTO.getNumberOfGuests(), fetchedReservation.getNumberOfGuests());
                assertEquals(reservationDTO.getReservationStatus(), fetchedReservation.getReservationStatus());
            }

        }

        // Metoda koja generira testne podatke
        private static Stream<Arguments> provideTestCases() {
            return Stream.of(
                    Arguments.of(133L, 200),  // Postojeći ID, očekivani status 200 (OK)
                    Arguments.of(999L, 404)  // Nepostojeći ID, očekivan rezultat 404 (NOT FOUND)
                    // Dodajte dodatne argumente prema potrebi
            );
        }

        

    }

