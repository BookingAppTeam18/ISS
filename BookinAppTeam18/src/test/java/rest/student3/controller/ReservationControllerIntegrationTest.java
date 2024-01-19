package rest.student3.controller;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import rest.domain.DTO.ReservationDTO;
import rest.domain.enumerations.ReservationStatus;
import rest.utils.TokenUtils;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ReservationControllerIntegrationTest{

    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private TokenUtils tokenUtils;

    @Test
    @DisplayName("Create reservations")
    public void shouldCreateReservation() throws Exception {
        // Kreiranje ReservationDTO objekta koji će biti poslat kao deo tela zahteva
        Date mockStartDate = new Date(2024, 01, 20);
        Date mockEndDate = new Date(2024, 01, 25);

        ReservationDTO reservationDTO = new ReservationDTO(1L, mockStartDate, mockEndDate, 4000, null, null, 2, ReservationStatus.CREATED);

        // Postavljanje zaglavlja HTTP zahteva
        String username = "guest@gmail.com";

        String jwtToken = tokenUtils.generateToken(username);

        // Postavljanje tokena u zaglavlje
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + jwtToken);

        HttpEntity<ReservationDTO> request = new HttpEntity<>(reservationDTO, headers);

        // Slanje POST zahteva ka odgovarajućem endpointu
        ResponseEntity<ReservationDTO> responseEntity = restTemplate.exchange(
                "/api/reservations",
                HttpMethod.POST,
                request,
                ReservationDTO.class
        );

        // Provera HTTP status koda
        assertEquals(201, responseEntity.getStatusCodeValue());

        // Provera povratnih podataka (vraćenog ReservationDTO objekta)
        ReservationDTO createdReservation = responseEntity.getBody();

    }


}
