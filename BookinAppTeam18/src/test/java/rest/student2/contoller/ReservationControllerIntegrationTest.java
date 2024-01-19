package rest.student2.contoller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import rest.domain.DTO.ReservationDTO;
import rest.domain.enumerations.ReservationStatus;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

    @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//    @ActiveProfiles("test")
    public class ReservationControllerIntegrationTest{

        @Autowired
        private TestRestTemplate restTemplate;

        @Test
        @DisplayName("Should change reservation status")
//        @WithUserDetails("owner")
        public void shouldChangeReservationStatus() {
            // Prvo, kreirajte rezervaciju koju želite ažurirati
//            ReservationDTO initialReservation = restTemplate.postForObject("/api/reservations",
//                    createSampleReservation(), ReservationDTO.class);
            ReservationDTO reservationDTO = new ReservationDTO(1L, null, null, 100.0, 1L, 1L, 1, ReservationStatus.CREATED);

            HttpEntity<ReservationDTO> requestEntity = new HttpEntity<ReservationDTO>(reservationDTO);


            ResponseEntity<ReservationDTO> responseEntity = restTemplate.exchange("/api/reservations/{reservationId}",
                    HttpMethod.PUT,
                    requestEntity,
                    ReservationDTO.class,
                    reservationDTO.getId());


//            ReservationDTO updatedReservation = responseEntity.getBody();

            assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
//            assertEquals(updatedReservation.getReservationStatus(), "APPROVED");
        }

        private ReservationDTO createSampleReservation() {
            // Implementirajte logiku za kreiranje rezervacije za test
            return new ReservationDTO(1L, null, null, 100.0, 1L, 1L, 1, ReservationStatus.CREATED);

        }

        private ReservationDTO createSampleUpdatedReservation(ReservationDTO initialReservation) {
            // Implementirajte logiku za ažuriranje rezervacije za test
            ReservationDTO updatedReservation = new ReservationDTO();
            updatedReservation.setId(initialReservation.getId());
            updatedReservation.setReservationStatus(ReservationStatus.APPROVED);
            // Dodajte ostale ažurirane podatke ako je potrebno
            return updatedReservation;
        }
    }

