package rest.student3.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import rest.domain.DTO.ReservationDTO;
import rest.domain.enumerations.ReservationStatus;
import rest.service.ReservationService;
import rest.utils.TokenUtils;

import java.util.Calendar;
import java.util.Date;


import static org.junit.jupiter.api.Assertions.assertEquals;

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
        Date mockStartDate = new Date();
        Date mockEndDate = new Date(mockStartDate.getTime());

        // Dodajte nedelju dana na datum završetka
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(mockEndDate);
        calendar.add(Calendar.DAY_OF_MONTH, 7);
        mockEndDate = calendar.getTime();

        ReservationDTO reservationDTO = new ReservationDTO(null, mockStartDate, mockEndDate, 4000, 1L, 3L, 2, ReservationStatus.CREATED);

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

        if (responseEntity.getStatusCodeValue() != HttpStatus.CREATED.value()) {
            try {
                ObjectMapper objectMapper = new ObjectMapper();
                String responseBodyJson = objectMapper.writeValueAsString(responseEntity.getBody());
                System.out.println("Response Body: " + responseBodyJson);
                String responseBodyJsonRequest = objectMapper.writeValueAsString(request.getBody());
                System.out.println("Request Body: " + responseBodyJsonRequest);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }

        // Provera HTTP status koda
        assertEquals(201, responseEntity.getStatusCodeValue());

    }

}

