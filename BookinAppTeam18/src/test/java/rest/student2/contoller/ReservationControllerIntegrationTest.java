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

import static org.junit.jupiter.api.Assertions.assertEquals;

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
            Date mockStartDate = new Date();
            Date mockEndDate = new Date(mockStartDate.getTime());

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
                    "/api/reservations/{reservationId}",
                    HttpMethod.PUT,
                    new HttpEntity<>(reservationDTO, headers),
                    ReservationDTO.class,
                    reservationId
            );

            // Provjera očekivanog status koda
            assertEquals(expectedStatusCode, responseEntity.getStatusCodeValue());
        }

        // Metoda koja generira testne podatke
        private static Stream<Arguments> provideTestCases() {
            return Stream.of(
                    Arguments.of(8L, 200),  // Postojeći ID, očekivani status 200 (OK)
                    Arguments.of(999L, 404)  // Nepostojeći ID, očekivan rezultat 404 (NOT FOUND)
                    // Dodajte dodatne argumente prema potrebi
            );
        }


    }

